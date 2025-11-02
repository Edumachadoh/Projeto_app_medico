package com.up.clinica_digital.presentation.appointment.doctor.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.AppointmentStatus
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
 * PEDRO:
 * ViewModel for the appointment details screen [AgendaDetailsScreen].
 *
 * Responsible for loading the details of a specific appointment (using the ID
 * received via [SavedStateHandle]) and the associated patient data.
 * Also manages the logic for canceling the appointment.
 *
 * @param getAppointmentByIdUseCase Use case to retrieve an appointment by ID.
 * @param getPatientByIdUseCase Use case to retrieve a patient by ID.
 * @param updateAppointmentUseCase Use case to update an appointment (e.g., cancel).
 * @param savedStateHandle Handle to access the navigation arguments (the "appointmentId").
 */
@HiltViewModel
class AgendaDetailsViewModel @Inject constructor(
    private val getAppointmentByIdUseCase: GetEntityByIdUseCase<Appointment>,
    private val getPatientByIdUseCase: GetEntityByIdUseCase<Patient>,
    private val updateAppointmentUseCase: UpdateEntityUseCase<Appointment>,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //PEDRO: store the appointment id
    private val appointmentId: String = savedStateHandle.get<String>("appointmentId")!!

    //PEDRO: store the screen state
    private val _uiState = MutableStateFlow<AgendaDetailsUiState>(AgendaDetailsUiState.Loading)
    val uiState: StateFlow<AgendaDetailsUiState> = _uiState.asStateFlow()

    //PEDRO: Load the initial appointment
    init {
        loadAppointmentDetails()
    }

    /**
     * PEDRO:
     * Loads the appointment and patient details using the [appointmentId].
     * Updates the [_uiState] with [AgendaDetailsUiState.Success] or
     * [AgendaDetailsUiState.Error] based on the result.
     */
    private fun loadAppointmentDetails() {

        // PEDRO: if any information fails, it returns
        // the error state
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
     * PEDRO:
     * Cancels the current appointment.
     *
     * This function updates the appointment status to [AppointmentStatus.CANCELED],
     * persists the change in the database, and then calls [onComplete]
     * (usually to close the screen).
     *
     * @param onComplete Callback executed after the cancellation is successful.
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