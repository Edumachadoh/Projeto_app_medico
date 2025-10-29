package com.up.clinica_digital.presentation.appointment.doctor.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.AppointmentStatus
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.Patient
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import com.up.clinica_digital.domain.usecase.UpdateEntityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para a tela de detalhes da agenda [AgendaDetailsScreen].
 *
 * Responsável por carregar os detalhes de uma consulta específica (usando o ID
 * recebido via [SavedStateHandle] e os dados do paciente associado.
 * Também gerencia a lógica para cancelar a consulta.
 *
 * @param getAppointmentByIdUseCase Caso de uso para buscar uma consulta pelo ID.
 * @param getPatientByIdUseCase Caso de uso para buscar um paciente pelo ID.
 * @param updateAppointmentUseCase Caso de uso para atualizar uma consulta (ex: cancelar).
 * @param savedStateHandle Handle para acessar os argumentos de navegação (o "appointmentId").
 */
@HiltViewModel
class AgendaDetailsViewModel @Inject constructor(
    private val getAppointmentByIdUseCase: GetEntityByIdUseCase<Appointment>,
    private val getPatientByIdUseCase: GetEntityByIdUseCase<Patient>,
    private val updateAppointmentUseCase: UpdateEntityUseCase<Appointment>,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //guardando o Id da consulta
    private val appointmentId: String = savedStateHandle.get<String>("appointmentId")!!

    //guardando estado da tela
    private val _uiState = MutableStateFlow<AgendaDetailsUiState>(AgendaDetailsUiState.Loading)
    val uiState: StateFlow<AgendaDetailsUiState> = _uiState.asStateFlow()

    //Carregando consulta ao iniciar
    init {
        loadAppointmentDetails()
    }

    /**
     * Carrega os detalhes da consulta e do paciente usando o [appointmentId].
     * Atualiza o [_uiState] com [AgendaDetailsUiState.Success] ou
     * [AgendaDetailsUiState.Error] com base no resultado.
     */
    private fun loadAppointmentDetails() {

        //se alguma informação der erro ele retorna
        //o estado de erro
        viewModelScope.launch {
            _uiState.value = AgendaDetailsUiState.Loading
            try {
                val appointment = getAppointmentByIdUseCase.invoke(appointmentId)
                if (appointment == null) {
                    _uiState.value = AgendaDetailsUiState.Error("Consulta não encontrada")
                    return@launch
                }

                val patient = getPatientByIdUseCase.invoke(appointment.patientId)
                if (patient == null) {
                    _uiState.value = AgendaDetailsUiState.Error("Médico não encontrado")
                    return@launch
                }

                _uiState.value = AgendaDetailsUiState.Success(appointment, patient)
            } catch (e: Exception) {
                _uiState.value = AgendaDetailsUiState.Error(e.message ?: "Ocorreu um erro")
            }
        }
    }

    /**
     * Cancela a consulta atual.
     *
     * Esta função atualiza o status da consulta para [AppointmentStatus.CANCELED],
     * persiste a mudança no banco de dados e, em seguida, chama [onComplete]
     * (geralmente para fechar a tela).
     *
     * @param onComplete Callback executado após o cancelamento ser bem-sucedido.
     */
    fun cancelAppointment(onComplete: () -> Unit) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is AgendaDetailsUiState.Success) {
                try {
                    val updatedAppointment = currentState.appointment.copy(
                        status = AppointmentStatus.CANCELED
                    )
                    updateAppointmentUseCase.invoke(updatedAppointment)
                    _uiState.value = currentState.copy(appointment = updatedAppointment)
                    onComplete()
                } catch (e: Exception) {
                    _uiState.value = AgendaDetailsUiState.Error(e.message.toString())
                }
            }
        }
    }
}