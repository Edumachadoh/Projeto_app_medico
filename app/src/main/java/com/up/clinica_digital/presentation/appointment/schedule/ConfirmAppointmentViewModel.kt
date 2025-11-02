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
 * PEDRO:
 * ViewModel for the appointment confirmation screen [ConfirmAppointmentScreen].
 *
 * This class is responsible for the logic of *confirming* and *saving* the new
 * appointment to the database when the user clicks the "Confirm" button.
 *
 * @param createAppointmentUseCase Use case to create a new [Appointment] entity.
 * @param getCurrentUserIdUseCase Use case to get the ID of the logged-in patient.
 */
@HiltViewModel
class ConfirmAppointmentViewModel @Inject constructor(
    private val createAppointmentUseCase: CreateEntityUseCase<Appointment>,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    //PEDRO: Storing screen state
    private val _uiState = MutableStateFlow(ConfirmAppointmentUiState())
    val uiState = _uiState.asStateFlow()

    /**
     * PEDRO:
     * Executes the logic to create and save the new appointment.
     *
     * @param doctorId The ID of the selected doctor.
     * @param dateTime The selected [LocalDateTime].
     */
    fun scheduleAppointment(doctorId: String, dateTime: LocalDateTime) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val patientId = getCurrentUserIdUseCase.invoke()

            if (patientId == null) {
                _uiState.update { it.copy(isLoading = false, error = "Usuário não autenticado.") }
                return@launch
            }

            //PEDRO: Creating new appointment object
            try {
                val newAppointment = Appointment(
                    id = UUID.randomUUID().toString(),
                    doctorId = doctorId,
                    patientId = patientId,
                    scheduledAt = dateTime,
                    status = AppointmentStatus.SCHEDULED
                )
                //PEDRO: Saving appointment in the database
                createAppointmentUseCase.invoke(newAppointment)
                _uiState.update { it.copy(isLoading = false, appointmentScheduled = true) }
            } catch (e: Exception) {
                //PEDRO:
                //With any error, it will not schedule
                //and will return an error to the screen state
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