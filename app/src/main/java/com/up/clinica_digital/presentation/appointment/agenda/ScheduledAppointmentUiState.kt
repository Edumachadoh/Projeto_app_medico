package com.up.clinica_digital.presentation.appointment.agenda

import com.up.clinica_digital.domain.model.Appointment

sealed class ScheduledAppointmentUiState {
    object Idle : ScheduledAppointmentUiState()
    object Loading : ScheduledAppointmentUiState()
    data class Success(val scheduledAppointments: List<Appointment>) : ScheduledAppointmentUiState()
    data class Error(val message: String) : ScheduledAppointmentUiState()
}