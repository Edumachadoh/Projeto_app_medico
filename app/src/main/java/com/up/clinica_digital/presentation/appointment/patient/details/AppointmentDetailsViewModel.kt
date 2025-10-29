package com.up.clinica_digital.presentation.appointment.patient.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.AppointmentStatus
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import com.up.clinica_digital.domain.usecase.UpdateEntityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * ViewModel para a tela de detalhes do agendamento do paciente (AppointmentDetailsScreen).
 *
 * Esta classe é responsável por:
 * 1. Obter o `appointmentId` passado pela navegação.
 * 2. Carregar os detalhes da consulta [Appointment] correspondente.
 * 3. Carregar os detalhes do médico [Doctor] associado a essa consulta.
 * 4. Expor o estado da UI [AppointmentDetailsUiState] para a tela.
 * 5. Gerenciar a lógica de cancelamento da consulta pelo paciente.
 *
 * @param getAppointmentByIdUseCase Caso de uso para buscar uma consulta pelo ID.
 * @param getDoctorByIdUseCase Caso de uso para buscar um médico pelo ID.
 * @param updateAppointmentUseCase Caso de uso para atualizar uma consulta.
 * @param savedStateHandle Handle para acessar os argumentos de navegação (o "appointmentId").
 */
@HiltViewModel
class AppointmentDetailsViewModel @Inject constructor(
    private val getAppointmentByIdUseCase: GetEntityByIdUseCase<Appointment>,
    private val getDoctorByIdUseCase: GetEntityByIdUseCase<Doctor>,
    private val updateAppointmentUseCase: UpdateEntityUseCase<Appointment>,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //guardando o Id da consulta
    private val appointmentId: String = savedStateHandle.get<String>("appointmentId")!!

    //guardando estado da tela
    private val _uiState = MutableStateFlow<AppointmentDetailsUiState>(AppointmentDetailsUiState.Loading)
    val uiState: StateFlow<AppointmentDetailsUiState> = _uiState.asStateFlow()

    //Carregando consulta ao iniciar
    init {
        loadAppointmentDetails()
    }

    /**
     * Carrega os detalhes da consulta e do médico associado.
     * Atualiza o [_uiState] para [AppointmentDetailsUiState.Success] ou
     * [AppointmentDetailsUiState.Error] dependendo do resultado.
     */
    private fun loadAppointmentDetails() {
        viewModelScope.launch {
            //se alguma informação der erro ele retorna
            //o estado de erro
            _uiState.value = AppointmentDetailsUiState.Loading
            try {
                val appointment = getAppointmentByIdUseCase.invoke(appointmentId)
                if (appointment == null) {
                    _uiState.value = AppointmentDetailsUiState.Error("Consulta não encontrada")
                    return@launch
                }

                val doctor = getDoctorByIdUseCase.invoke(appointment.doctorId)
                if (doctor == null) {
                    _uiState.value = AppointmentDetailsUiState.Error("Médico não encontrado")
                    return@launch
                }

                _uiState.value = AppointmentDetailsUiState.Success(appointment, doctor)
            } catch (e: Exception) {
                _uiState.value = AppointmentDetailsUiState.Error(e.message ?: "Ocorreu um erro")
            }
        }
    }

    /**
     * Cancela a consulta atual.
     *
     * Altera o status da consulta para [AppointmentStatus.CANCELED],
     * atualiza no repositório e executa [onComplete] em caso de sucesso
     * (geralmente usado para navegar de volta).
     *
     * @param onComplete Callback a ser executado após o sucesso do cancelamento.
     */
    fun cancelAppointment(onComplete: () -> Unit) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is AppointmentDetailsUiState.Success) {
                try {
                    val updatedAppointment = currentState.appointment.copy(
                        status = AppointmentStatus.CANCELED
                    )
                    updateAppointmentUseCase.invoke(updatedAppointment)
                    _uiState.value = currentState.copy(appointment = updatedAppointment)
                    onComplete()
                } catch (e: Exception) {
                    _uiState.value = AppointmentDetailsUiState.Error(e.message.toString())
                }
            }
        }
    }
}