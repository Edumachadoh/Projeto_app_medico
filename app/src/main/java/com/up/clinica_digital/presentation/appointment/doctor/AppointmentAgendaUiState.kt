package com.up.clinica_digital.presentation.appointment.doctor

import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Patient

/**
 * PEDRO:
 * Represents the possible states of the user interface (UI)
 * for the doctor's appointment agenda screen.
 *
 * This sealed class defines the different scenarios that the UI can display,
 * such as "loading", "success" (with data), or "error".
 * Used by [AppointmentAgendaViewModel]
 *
 * @property Loading When the screen is loading
 * @property Success When the screen loads successfully
 * @property Error When an error occurs while loading
 */

sealed class AppointmentAgendaUiState {
    object Loading : AppointmentAgendaUiState()
    data class Success(
        val scheduledAppointments: List<Appointment>,
        val patients: Map<String, Patient>
    ) : AppointmentAgendaUiState()
    data class Error(val message: String) : AppointmentAgendaUiState()
}