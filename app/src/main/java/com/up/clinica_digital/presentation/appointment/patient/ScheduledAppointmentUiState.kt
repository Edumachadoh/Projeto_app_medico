package com.up.clinica_digital.presentation.appointment.patient

import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Doctor
/**
 * Representa os possíveis estados da interface de usuário (UI)
 * para a tela de consultas agendadas do paciente (ScheduledAppointmentsScreen).
 */

sealed class ScheduledAppointmentUiState {
    object Loading : ScheduledAppointmentUiState()
    data class Success(
        val scheduledAppointments: List<Appointment>,
        val doctors: Map<String, Doctor>
    ) : ScheduledAppointmentUiState()
    data class Error(val message: String) : ScheduledAppointmentUiState()
}