package com.up.clinica_digital.presentation.appointment.patient

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import com.up.clinica_digital.domain.usecase.appointment.ListByPatientUseCase
import com.up.clinica_digital.domain.usecase.user.GetCurrentUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduledAppointmentViewModel @Inject constructor(
    private val getPatientScheduledAppointmentsUseCase: ListByPatientUseCase,
    private val getDoctorByIdUseCase: GetEntityByIdUseCase<Doctor>,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScheduledAppointmentUiState>(ScheduledAppointmentUiState.Loading)
    val uiState: StateFlow<ScheduledAppointmentUiState> = _uiState.asStateFlow()

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private var allAppointments = listOf<Appointment>()
    private val doctorsMap = mutableMapOf<String, Doctor>()

    init {
        loadInitialAppointments()
    }

    private fun loadInitialAppointments() {
        viewModelScope.launch {
            _uiState.value = ScheduledAppointmentUiState.Loading
            try {
                val patientId = getCurrentUserIdUseCase.invoke()

                if (patientId == null) {
                    _uiState.value = ScheduledAppointmentUiState.Error("Usuário não autenticado")
                    return@launch
                }

                allAppointments = getPatientScheduledAppointmentsUseCase.invoke(patientId)

                allAppointments.map { it.doctorId }.distinct().forEach { doctorId ->
                    if (!doctorsMap.containsKey(doctorId)) {
                        getDoctorByIdUseCase.invoke(doctorId)?.let { doctor ->
                            doctorsMap[doctorId] = doctor
                        }
                    }
                }

                _uiState.value = ScheduledAppointmentUiState.Success(
                    scheduledAppointments = allAppointments,
                    doctors = doctorsMap
                )
            } catch (e: Exception) {
                _uiState.value = ScheduledAppointmentUiState.Error(e.message ?: "Erro desconhecido")
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
                val doctor = doctorsMap[appointment.doctorId]
                doctor?.name?.contains(query, ignoreCase = true) == true
            }
        }
        _uiState.value = ScheduledAppointmentUiState.Success(
            scheduledAppointments = filteredList,
            doctors = doctorsMap
        )
    }
}