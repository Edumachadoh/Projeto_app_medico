package com.up.clinica_digital.presentation.appointment

import com.up.clinica_digital.domain.model.Doctor

sealed class DoctorUiState {
    object Idle : DoctorUiState()
    object Loading : DoctorUiState()
    data class Success(val doctor: Doctor) : DoctorUiState()
    data class Error(val message: String) : DoctorUiState()
}