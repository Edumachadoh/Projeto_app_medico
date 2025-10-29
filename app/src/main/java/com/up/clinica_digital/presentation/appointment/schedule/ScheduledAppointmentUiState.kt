package com.up.clinica_digital.presentation.appointment.schedule

import com.up.clinica_digital.domain.model.Doctor
import java.time.LocalDateTime

// estados da tela de agendamento

data class AppointmentScheduleUiState(
    val isLoading: Boolean = false,
    val doctor: Doctor? = null,
    val selectedDateTime: LocalDateTime? = null,
    val appointmentScheduled: Boolean = false,
    val error: String? = null
)