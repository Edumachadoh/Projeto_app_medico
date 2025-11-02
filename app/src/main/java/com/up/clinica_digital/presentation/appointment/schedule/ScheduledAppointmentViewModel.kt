package com.up.clinica_digital.presentation.appointment.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * PEDRO:
 * ViewModel for the appointment scheduling screen (AppointmentScheduleScreen).
 *
 * Responsible for:
 * 1. Loading the doctor's data ([Doctor]) based on the ID received from navigation.
 * 2. Storing the date and time ([LocalDateTime]) that the user selects on the calendar.
 *
 * @param getDoctorUseCase Use case to fetch a doctor by ID.
 */
@HiltViewModel
class AppointmentScheduleViewModel @Inject constructor(
    private val getDoctorUseCase: GetEntityByIdUseCase<Doctor>
) : ViewModel() {

    //PEDRO: Appointment scheduling screen states
    private val _uiState = MutableStateFlow(AppointmentScheduleUiState())
    val uiState = _uiState.asStateFlow()

    /**
     * PEDRO:
     * Loads the doctor's data using the provided ID (received from navigation).
     *
     * @param doctorId The ID of the doctor to be loaded.
     */
    fun loadDoctor(doctorId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val doctor = getDoctorUseCase.invoke(doctorId)
                _uiState.update { it.copy(doctor = doctor, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Erro desconhecido",
                        isLoading = false
                    )
                }
            }
        }
    }

    /**
     * PEDRO:
     * Updates the UI state with the date and time selected by the user.
     *
     * @param dateTime The [LocalDateTime] that the user chose in the component.
     */
    fun onDateTimeSelected(dateTime: LocalDateTime) {
        _uiState.update { it.copy(selectedDateTime = dateTime) }
    }
}