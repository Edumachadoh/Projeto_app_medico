package com.up.clinica_digital.presentation.appointment.schedule

import com.up.clinica_digital.domain.model.Doctor
import java.time.LocalDateTime

data class AppointmentScheduleUiState(
    val doctor: Doctor? = null,
    val selectedDateTime: LocalDateTime? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val appointmentScheduled: Boolean = false
)