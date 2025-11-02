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
 * PEDRO:
 * ViewModel for the patient's appointment details screen (AppointmentDetailsScreen).
 *
 * This class is responsible for:
 * 1. Getting the `appointmentId` passed via navigation.
 * 2. Loading the details of the corresponding [Appointment].
 * 3. Loading the details of the [Doctor] associated with that appointment.
 * 4. Exposing the [AppointmentDetailsUiState] to the screen.
 * 5. Managing the logic for canceling the appointment by the patient.
 *
 * @param getAppointmentByIdUseCase Use case to fetch an appointment by ID.
 * @param getDoctorByIdUseCase Use case to fetch a doctor by ID.
 * @param updateAppointmentUseCase Use case to update an appointment.
 * @param savedStateHandle Handle to access navigation arguments (the "appointmentId").
 */
@HiltViewModel
class AppointmentDetailsViewModel @Inject constructor(
    private val getAppointmentByIdUseCase: GetEntityByIdUseCase<Appointment>,
    private val getDoctorByIdUseCase: GetEntityByIdUseCase<Doctor>,
    private val updateAppointmentUseCase: UpdateEntityUseCase<Appointment>,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //PEDRO: Storing the appointment Id
    private val appointmentId: String = savedStateHandle.get<String>("appointmentId")!!

    //PEDRO: Storing screen state
    private val _uiState = MutableStateFlow<AppointmentDetailsUiState>(AppointmentDetailsUiState.Loading)
    val uiState: StateFlow<AppointmentDetailsUiState> = _uiState.asStateFlow()

    //PEDRO: Loading appointment on init
    init {
        loadAppointmentDetails()
    }

    /**
     * PEDRO:
     * Loads the details of the appointment and the associated doctor.
     * Updates the [_uiState] to [AppointmentDetailsUiState.Success] or
     * [AppointmentDetailsUiState.Error] depending on the result.
     */
    private fun loadAppointmentDetails() {
        viewModelScope.launch {
            //PEDRO: If any information fails, it returns
            //the error state
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
     * PEDRO:
     * Cancels the current appointment.
     *
     * Changes the appointment status to [AppointmentStatus.CANCELED],
     * updates it in the repository, and executes [onComplete] on success
     * (usually used to navigate back).
     *
     * @param onComplete Callback to be executed after successful cancellation.
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