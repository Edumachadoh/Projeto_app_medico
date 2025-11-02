package com.up.clinica_digital.presentation.appointment.patient

import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Doctor
/**
 * PEDRO:
 * Represents the possible states of the user interface (UI)
 * for the patient's scheduled appointments screen (ScheduledAppointmentsScreen).
 */

sealed class ScheduledAppointmentUiState {
    object Loading : ScheduledAppointmentUiState()
    data class Success(
        val scheduledAppointments: List<Appointment>,
        val doctors: Map<String, Doctor>
    ) : ScheduledAppointmentUiState()
    data class Error(val message: String) : ScheduledAppointmentUiState()
}