package com.up.clinica_digital.presentation.appointment.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.AppointmentStatus
import com.up.clinica_digital.domain.usecase.CreateEntityUseCase
import com.up.clinica_digital.domain.usecase.user.GetCurrentUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

/**
 * ViewModel para a tela de confirmação de agendamento [ConfirmAppointmentScreen].
 *
 * Esta classe é responsável pela lógica de *confirmar* e *salvar* o novo
 * agendamento no banco de dados quando o usuário clica no botão "Confirmar".
 *
 * @param createAppointmentUseCase Caso de uso para criar uma nova entidade [Appointment].
 * @param getCurrentUserIdUseCase Caso de uso para obter o ID do paciente logado.
 */
@HiltViewModel
class ConfirmAppointmentViewModel @Inject constructor(
    private val createAppointmentUseCase: CreateEntityUseCase<Appointment>,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    //guardando estado da tela
    private val _uiState = MutableStateFlow(ConfirmAppointmentUiState())
    val uiState = _uiState.asStateFlow()

    /**
     * Executa a lógica para criar e salvar o novo agendamento.
     *
     * @param doctorId O ID do médico selecionado.
     * @param dateTime A data e hora [LocalDateTime] selecionados.
     */
    fun scheduleAppointment(doctorId: String, dateTime: LocalDateTime) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val patientId = getCurrentUserIdUseCase.invoke()

            if (patientId == null) {
                _uiState.update { it.copy(isLoading = false, error = "Usuário não autenticado.") }
                return@launch
            }

            //criando novo objeto de consulta
            try {
                val newAppointment = Appointment(
                    id = UUID.randomUUID().toString(),
                    doctorId = doctorId,
                    patientId = patientId,
                    scheduledAt = dateTime,
                    status = AppointmentStatus.SCHEDULED
                )
                //guardando consulta no banco
                createAppointmentUseCase.invoke(newAppointment)
                _uiState.update { it.copy(isLoading = false, appointmentScheduled = true) }
            } catch (e: Exception) {
                //com qualquer erro não irá agendar
                //e retornar um erro ao estado da tela
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Ocorreu um erro ao agendar a consulta."
                    )
                }
            }
        }
    }
}