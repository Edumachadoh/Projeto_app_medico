package com.up.clinica_digital.presentation.appointment.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.AppointmentStatus
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.usecase.CreateEntityUseCase
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import com.up.clinica_digital.domain.usecase.user.GetCurrentUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AppointmentScheduleViewModel @Inject constructor(
    private val appointmentScheduleUseCase: CreateEntityUseCase<Appointment>,
    private val getDoctorUseCase: GetEntityByIdUseCase<Doctor>,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppointmentScheduleUiState())
    val uiState: StateFlow<AppointmentScheduleUiState> = _uiState.asStateFlow()

    fun loadDoctor(doctorId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val doctor = getDoctorUseCase.invoke(doctorId)
                if (doctor != null) {
                    _uiState.update { it.copy(doctor = doctor, isLoading = false) }
                } else {
                    _uiState.update { it.copy(error = "Médico não encontrado", isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Erro desconhecido", isLoading = false) }
            }
        }
    }

    fun onDateTimeSelected(dateTime: LocalDateTime) {
        _uiState.update { it.copy(selectedDateTime = dateTime) }
    }

    fun scheduleAppointment(doctorId: String) {
        viewModelScope.launch {
            val patientId = getCurrentUserIdUseCase.invoke()
            if (patientId == null) {
                _uiState.update { it.copy(error = "Usuário não autenticado") }
                return@launch
            }

            val currentState = _uiState.value
            val selectedDateTime = currentState.selectedDateTime

            if (selectedDateTime == null) {
                _uiState.update { it.copy(error = "Data e hora não selecionadas") }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, error = null) }

            val appointment = Appointment(
                id = UUID.randomUUID().toString(),
                doctorId = doctorId,
                patientId = patientId,
                scheduledAt = selectedDateTime,
                status = AppointmentStatus.SCHEDULED
            )

            try {
                val success = appointmentScheduleUseCase.invoke(appointment)
                if (success) {
                    _uiState.update { it.copy(appointmentScheduled = true, isLoading = false) }
                } else {
                    _uiState.update { it.copy(error = "Falha ao agendar consulta", isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Erro desconhecido", isLoading = false) }
            }
        }
    }
}