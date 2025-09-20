package com.up.clinica_digital.presentation.appointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.AppointmentStatus
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.usecase.CreateEntityUseCase
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
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
class AppointmentViewModel @Inject constructor(
    private val appointmentScheduleUseCase: CreateEntityUseCase<Appointment>,
    private val getDoctorUseCase: GetEntityByIdUseCase<Doctor>
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppointmentScheduleUiState())
    val uiState: StateFlow<AppointmentScheduleUiState> = _uiState.asStateFlow()

    fun loadDoctor(doctorId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val doctor = getDoctorUseCase.invoke(doctorId)
                _uiState.update { it.copy(doctor = doctor, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Erro desconhecido", isLoading = false) }
            }
        }
    }

    fun onDateTimeSelected(dateTime: LocalDateTime) {
        _uiState.update { it.copy(selectedDateTime = dateTime) }
    }

    fun scheduleAppointment(patientId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val currentState = _uiState.value
            val doctor = currentState.doctor
            val selectedDateTime = currentState.selectedDateTime

            if (doctor == null || selectedDateTime == null) {
                _uiState.update { it.copy(error = "Médico ou data não selecionados", isLoading = false) }
                return@launch
            }

            val appointment = Appointment(
                id = UUID.randomUUID().toString(),
                doctorId = doctor.id,
                patientId = patientId, // Você precisará obter o ID do paciente logado
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