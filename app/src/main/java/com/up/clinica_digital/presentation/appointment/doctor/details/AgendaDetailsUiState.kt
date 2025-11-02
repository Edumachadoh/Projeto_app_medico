package com.up.clinica_digital.presentation.appointment.doctor.details

import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Patient

/**
 * PEDRO:
 * Represents the possible user interface (UI) states
 * for the appointment details screen [AgendaDetailsScreen].
 */
sealed class AgendaDetailsUiState {
    object Loading : AgendaDetailsUiState()
    data class Success(
        val appointment: Appointment,
        val patient: Patient
    ) : AgendaDetailsUiState()
    data class Error(val message: String) : AgendaDetailsUiState()
}