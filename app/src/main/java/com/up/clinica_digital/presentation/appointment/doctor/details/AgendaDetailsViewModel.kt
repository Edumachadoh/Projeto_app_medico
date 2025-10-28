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

/*
* Viewmodel que mostra os detalhes
* do paciente na consulta que o médico
* selecionou na tela anterior que é a
* AppointmentAgendaScreen
* */
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

    //Carregando consulta
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

    //Função para cancelar consulta
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