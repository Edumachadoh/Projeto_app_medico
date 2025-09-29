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

@HiltViewModel
class ConfirmAppointmentViewModel @Inject constructor(
    private val createAppointmentUseCase: CreateEntityUseCase<Appointment>,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfirmAppointmentUiState())
    val uiState = _uiState.asStateFlow()

    fun scheduleAppointment(doctorId: String, dateTime: LocalDateTime) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val patientId = getCurrentUserIdUseCase.invoke()

            if (patientId == null) {
                _uiState.update { it.copy(isLoading = false, error = "Usuário não autenticado.") }
                return@launch
            }

            try {
                val newAppointment = Appointment(
                    id = UUID.randomUUID().toString(),
                    doctorId = doctorId,
                    patientId = patientId,
                    scheduledAt = dateTime,
                    status = AppointmentStatus.SCHEDULED
                )
                createAppointmentUseCase.invoke(newAppointment)
                _uiState.update { it.copy(isLoading = false, appointmentScheduled = true) }
            } catch (e: Exception) {
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