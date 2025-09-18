package com.up.clinica_digital.presentation.appointment

sealed class AppointmentUiState {
    object Idle : AppointmentUiState()
    object Loading : AppointmentUiState()
    data class Success(val userId: String) : AppointmentUiState()
    data class Error(val message: String) : AppointmentUiState()
}