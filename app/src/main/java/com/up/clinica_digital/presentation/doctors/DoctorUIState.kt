package com.up.clinica_digital.presentation.doctor
import com.up.clinica_digital.domain.model.Doctor

data class DoctorUIState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val doctors: List<Doctor> = emptyList(),
    var selectedDoctor: Doctor = null,
    val error: String? = null
)