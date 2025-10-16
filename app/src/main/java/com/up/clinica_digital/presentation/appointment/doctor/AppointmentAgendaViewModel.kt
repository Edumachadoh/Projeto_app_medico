package com.up.clinica_digital.presentation.appointment.doctor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Patient
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import com.up.clinica_digital.domain.usecase.appointment.ListByDoctorUseCase
import com.up.clinica_digital.domain.usecase.user.GetCurrentUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentAgendaViewModel @Inject constructor(
    private val getDoctorAgendaAppointmentsUseCase: ListByDoctorUseCase,
    private val getPatientByIdUseCase: GetEntityByIdUseCase<Patient>,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AppointmentAgendaUiState>(AppointmentAgendaUiState.Loading)
    val uiState: StateFlow<AppointmentAgendaUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var allAppointments = listOf<Appointment>()
    private val patientsMap = mutableMapOf<String, Patient>()

    init {
        loadInitialAppointments()
    }

    private fun loadInitialAppointments() {
        viewModelScope.launch {
            _uiState.value = AppointmentAgendaUiState.Loading
            try {
                val doctorId = getCurrentUserIdUseCase.invoke()

                if (doctorId == null) {
                    _uiState.value = AppointmentAgendaUiState.Error("Médico não autenticado")
                    return@launch
                }

                allAppointments = getDoctorAgendaAppointmentsUseCase.invoke(doctorId)

                allAppointments.forEach { appointment ->
                    val patientId = appointment.patientId
                    if (!patientsMap.containsKey(patientId)) {
                        getPatientByIdUseCase.invoke(patientId)?.let { patient ->
                            patientsMap[patientId] = patient
                        }
                    }
                }

                _uiState.value = AppointmentAgendaUiState.Success(
                    scheduledAppointments = allAppointments,
                    patients = patientsMap
                )
            } catch (e: Exception) {
                _uiState.value = AppointmentAgendaUiState.Error(e.message ?: "Erro desconhecido ao carregar a agenda")
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterAppointments(query)
    }

    private fun filterAppointments(query: String) {
        val filteredList = if (query.isBlank()) {
            allAppointments
        } else {
            allAppointments.filter { appointment ->
                val patient = patientsMap[appointment.patientId]
                patient?.name?.contains(query, ignoreCase = true) == true
            }
        }
        if (_uiState.value is AppointmentAgendaUiState.Success){
            _uiState.update {
                (it as AppointmentAgendaUiState.Success).copy(scheduledAppointments = filteredList)
            }
        }
    }
}