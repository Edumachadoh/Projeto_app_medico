package com.up.clinica_digital.presentation.appointment.doctor

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.Patient
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import com.up.clinica_digital.domain.usecase.appointment.ListByDoctorUseCase
import com.up.clinica_digital.domain.usecase.appointment.ListByPatientUseCase
import com.up.clinica_digital.domain.usecase.user.GetCurrentUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private var allAppointments = listOf<Appointment>()
    private val patientMap = mutableMapOf<String, Patient>()

    init {
        loadInitialAppointments()
    }

    private fun loadInitialAppointments() {
        viewModelScope.launch {
            _uiState.value = AppointmentAgendaUiState.Loading
            try {
                val doctorId = getCurrentUserIdUseCase.invoke()

                if (doctorId == null) {
                    _uiState.value = AppointmentAgendaUiState.Error("Usuário não autenticado")
                    return@launch
                }

                allAppointments = getDoctorAgendaAppointmentsUseCase.invoke(doctorId)

                allAppointments.map { it.patientId }.distinct().forEach { patientId ->
                    if (!patientMap.containsKey(patientId)) {
                        getPatientByIdUseCase.invoke(patientId)?.let { patient ->
                            patientMap[patientId] = patient
                        }
                    }
                }

                _uiState.value = AppointmentAgendaUiState.Success(
                    scheduledAppointments = allAppointments,
                    patients = patientMap
                )
            } catch (e: Exception) {
                _uiState.value = AppointmentAgendaUiState.Error(e.message ?: "Erro desconhecido")
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
                val patient = patientMap[appointment.doctorId]
                patient?.name?.contains(query, ignoreCase = true) == true
            }
        }
        _uiState.value = AppointmentAgendaUiState.Success(
            scheduledAppointments = filteredList,
            patients = patientMap
        )
    }
}