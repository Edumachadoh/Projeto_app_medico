package com.up.clinica_digital.presentation.appointment.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * ViewModel para a tela de agendamento (AppointmentScheduleScreen).
 *
 * Responsável por:
 * 1. Carregar os dados do médico ([Doctor]) com base no ID recebido da navegação.
 * 2. Armazenar a data e hora ([LocalDateTime]) que o usuário seleciona no calendário.
 *
 * @param getDoctorUseCase Caso de uso para buscar um médico pelo ID.
 */
@HiltViewModel
class AppointmentScheduleViewModel @Inject constructor(
    private val getDoctorUseCase: GetEntityByIdUseCase<Doctor>
) : ViewModel() {

    //estados da tela de agendamento
    private val _uiState = MutableStateFlow(AppointmentScheduleUiState())
    val uiState = _uiState.asStateFlow()

    /**
     * Carrega os dados do médico usando o ID fornecido (recebido da navegação).
     *
     * @param doctorId O ID do médico a ser carregado.
     */
    fun loadDoctor(doctorId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val doctor = getDoctorUseCase.invoke(doctorId)
                _uiState.update { it.copy(doctor = doctor, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Erro desconhecido",
                        isLoading = false
                    )
                }
            }
        }
    }

    /**
     * Atualiza o estado da UI com a data e hora selecionadas pelo usuário.
     *
     * @param dateTime O [LocalDateTime] que o usuário escolheu no componente.
     */
    fun onDateTimeSelected(dateTime: LocalDateTime) {
        _uiState.update { it.copy(selectedDateTime = dateTime) }
    }
}