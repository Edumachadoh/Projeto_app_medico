package com.up.clinica_digital.presentation.auth

import com.up.clinica_digital.domain.model.UserRole

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val userId: String, val role: UserRole) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}
