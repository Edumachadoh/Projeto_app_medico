package com.up.clinica_digital.presentation.appointment.doctor

import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.Patient

/*
*   Guardando estados da tela para
*   mudar de carregar para sucesso
*   ou se erro para erro
*/

sealed class AppointmentAgendaUiState {
    object Loading : AppointmentAgendaUiState()
    data class Success(
        val scheduledAppointments: List<Appointment>,
        val patients: Map<String, Patient>
    ) : AppointmentAgendaUiState()
    data class Error(val message: String) : AppointmentAgendaUiState()
}