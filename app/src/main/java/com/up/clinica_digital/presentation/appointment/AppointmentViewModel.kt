package com.up.clinica_digital.presentation.appointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.usecase.CreateEntityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val appointmentScheduleUseCase: CreateEntityUseCase<Appointment>
) : ViewModel() {

    private val _appointmentState = MutableStateFlow<AppointmentUiState>(
        AppointmentUiState.Idle)
    val appointmentState: StateFlow<AppointmentUiState> = _appointmentState

    fun schedule(appointment: Appointment){
        viewModelScope.launch {
            _appointmentState.value = AppointmentUiState.Loading

            try {
                val success = appointmentScheduleUseCase.invoke(appointment)
                if(success) {
                    _appointmentState.value = AppointmentUiState.Success(appointment.id)
                }else{
                    _appointmentState.value = AppointmentUiState.Error("Falha ao agendar consulta")
                }

            } catch (e: Exception) {
                _appointmentState.value = AppointmentUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}