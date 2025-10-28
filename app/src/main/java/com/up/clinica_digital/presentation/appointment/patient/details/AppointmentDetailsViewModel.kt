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
/*
* Viewmodel que mostra os detalhes
* da consulta que o paciente
* selecionou na tela anterior que é a
* ScheduledAppointmentsScreen
* */
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

    //Carregando consulta
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
    //Função para cancelar consulta
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