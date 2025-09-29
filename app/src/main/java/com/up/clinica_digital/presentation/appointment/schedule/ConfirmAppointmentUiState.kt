package com.up.clinica_digital.presentation.appointment.schedule

data class ConfirmAppointmentUiState(
    val isLoading: Boolean = false,
    val appointmentScheduled: Boolean = false,
    val error: String? = null
)