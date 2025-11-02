package com.up.clinica_digital.presentation.appointment.schedule

import com.up.clinica_digital.domain.model.Doctor
import java.time.LocalDateTime

/**
 * PEDRO:
 * Represents the states of the user interface (UI) for the
 * appointment selection screen (AppointmentScheduleScreen).
 *
 * @property isLoading Indicates if the initial data (e.g., doctor information)
 * is being loaded.
 * @property doctor The [Doctor] object for whom the appointment is being made.
 * @property selectedDateTime The [LocalDateTime] date and time that the user
 * selected on the calendar.
 * @property appointmentScheduled (This property seems not to be used
 * on this screen, but rather on the confirmation screen).
 * @property error Contains an error message, in case a failure occurs
 * (e.g., doctor not found).
 */
data class AppointmentScheduleUiState(
    val isLoading: Boolean = false,
    val doctor: Doctor? = null,
    val selectedDateTime: LocalDateTime? = null,
    val appointmentScheduled: Boolean = false,
    val error: String? = null
)