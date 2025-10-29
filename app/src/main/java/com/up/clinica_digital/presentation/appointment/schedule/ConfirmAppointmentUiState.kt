package com.up.clinica_digital.presentation.appointment.schedule

/**
 * Representa o estado da interface de usuário (UI) para a tela
 * de confirmação de agendamento (ConfirmAppointmentScreen).
 *
 * @property isLoading Indica se a operação de salvar o agendamento
 * está em andamento.
 * @property appointmentScheduled Indica se o agendamento foi salvo
 * com sucesso (usado para exibir a mensagem de sucesso).
 * @property error Contém uma mensagem de erro, caso a operação falhe.
 */
data class ConfirmAppointmentUiState(
    val isLoading: Boolean = false,
    val appointmentScheduled: Boolean = false,
    val error: String? = null
)