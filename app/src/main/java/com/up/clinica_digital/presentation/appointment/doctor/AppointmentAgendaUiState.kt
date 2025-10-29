package com.up.clinica_digital.presentation.appointment.doctor

import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.Patient

/**
 * Representa os possíveis estados da interface de usuário (UI)
 * para a tela da agenda de consultas do médico.
 *
 * Esta classe selada define os diferentes cenários que a UI pode exibir,
 * como "carregando", "sucesso" (com os dados) ou "erro".
 * utilizado pelo [AppointmentAgendaViewModel]
 *
 * @property Loading Quando a tela está carregando
 * @property Success Quando a tela consegue carregar
 * @property Error Quando ocorre algum erro ao carregar
 */

sealed class AppointmentAgendaUiState {
    object Loading : AppointmentAgendaUiState()
    data class Success(
        val scheduledAppointments: List<Appointment>,
        val patients: Map<String, Patient>
    ) : AppointmentAgendaUiState()
    data class Error(val message: String) : AppointmentAgendaUiState()
}