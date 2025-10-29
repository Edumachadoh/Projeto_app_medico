package com.up.clinica_digital.presentation.appointment.schedule

import com.up.clinica_digital.domain.model.Doctor
import java.time.LocalDateTime

/**
 * Representa os estados da interface de usuário (UI) para a tela
 * de seleção de agendamento (AppointmentScheduleScreen).
 *
 * @property isLoading Indica se os dados iniciais (ex: informações do médico)
 * estão sendo carregados.
 * @property doctor O objeto [Doctor] para o qual o agendamento está sendo feito.
 * @property selectedDateTime A data e hora [LocalDateTime] que o usuário
 * selecionou no calendário.
 * @property appointmentScheduled (Esta propriedade parece não ser usada
 * nesta tela, mas sim na tela de confirmação).
 * @property error Contém uma mensagem de erro, caso ocorra uma falha
 * (ex: médico não encontrado).
 */
data class AppointmentScheduleUiState(
    val isLoading: Boolean = false,
    val doctor: Doctor? = null,
    val selectedDateTime: LocalDateTime? = null,
    val appointmentScheduled: Boolean = false,
    val error: String? = null
)