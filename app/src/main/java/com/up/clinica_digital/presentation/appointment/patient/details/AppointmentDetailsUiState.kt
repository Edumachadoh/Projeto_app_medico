package com.up.clinica_digital.presentation.appointment.patient.details

import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Doctor

/**
 * PEDRO:
 * Represents the possible states of the user interface (UI)
 * for the patient's appointment details screen [AppointmentDetailsScreen].
 */
sealed class AppointmentDetailsUiState {
    object Loading : AppointmentDetailsUiState()
    data class Success(
        val appointment: Appointment,
        val doctor: Doctor
    ) : AppointmentDetailsUiState()
    data class Error(val message: String) : AppointmentDetailsUiState()
}