package com.up.clinica_digital.presentation.appointment.schedule

//estados para a tela confirmar agendamento
data class ConfirmAppointmentUiState(
    val isLoading: Boolean = false,
    val appointmentScheduled: Boolean = false,
    val error: String? = null
)