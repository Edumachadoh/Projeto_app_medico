package com.up.clinica_digital.presentation.appointment.schedule

/**
 * PEDRO:
 * Represents the state of the user interface (UI) for the
 * appointment confirmation screen (ConfirmAppointmentScreen).
 *
 * @property isLoading Indicates if the operation to save the appointment
 * is in progress.
 * @property appointmentScheduled Indicates if the appointment was saved
 * successfully (used to display the success message).
 * @property error Contains an error message if the operation fails.
 */
data class ConfirmAppointmentUiState(
    val isLoading: Boolean = false,
    val appointmentScheduled: Boolean = false,
    val error: String? = null
)