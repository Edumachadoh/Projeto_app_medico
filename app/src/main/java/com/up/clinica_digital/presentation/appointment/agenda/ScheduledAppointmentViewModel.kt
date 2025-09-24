package com.up.clinica_digital.presentation.appointment.agenda

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import com.up.clinica_digital.domain.usecase.appointment.ListByPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduledAppointmentViewModel @Inject constructor(
    private val getPatientScheduledAppointmentsUseCase: ListByPatientUseCase,
    private val getDoctorByIdUseCase: GetEntityByIdUseCase<Doctor>
) : ViewModel() {

    private var allScheduledAppointments = listOf<Appointment>()

    private val _scheduledAppointmentUiState = MutableStateFlow<ScheduledAppointmentUiState>(
        ScheduledAppointmentUiState.Idle)
    val scheduledAppointmentUiState: StateFlow<ScheduledAppointmentUiState> = _scheduledAppointmentUiState

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    init {
        loadInitialAppointments()
    }

    private fun loadInitialAppointments() {
        viewModelScope.launch {
            _scheduledAppointmentUiState.update { ScheduledAppointmentUiState.Loading }
            try {
                val appointments = getPatientScheduledAppointmentsUseCase.invoke("1")
                allScheduledAppointments = appointments
                _scheduledAppointmentUiState.update { ScheduledAppointmentUiState.Success(appointments) }
            } catch (e: Exception) {
                _scheduledAppointmentUiState.update { ScheduledAppointmentUiState.Error(e.message ?: "Erro desconhecido") }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterAppointments(query)
    }

    private fun filterAppointments(query: String) {

        val filteredList = if (query.isBlank()) {
            allScheduledAppointments
        } else {
            allScheduledAppointments.filter { appointment ->
                val doctor = getDoctorByIdUseCase.invoke(appointment.doctorId)
                doctor?.name?.contains(query, ignoreCase = true) == true
            }
        }
        _scheduledAppointmentUiState.update { ScheduledAppointmentUiState.Success(filteredList) }
    }
}