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

@HiltViewModel
class AppointmentScheduleViewModel @Inject constructor(
    private val getDoctorUseCase: GetEntityByIdUseCase<Doctor>
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppointmentScheduleUiState())
    val uiState = _uiState.asStateFlow()

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

    fun onDateTimeSelected(dateTime: LocalDateTime) {
        _uiState.update { it.copy(selectedDateTime = dateTime) }
    }
}