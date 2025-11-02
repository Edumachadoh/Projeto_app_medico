package com.up.clinica_digital.presentation.auth

import com.up.clinica_digital.domain.model.UserRole

/**
 * PEDRO:
 * Represents the user interface (UI) states for
 * both the registration and login screens [LoginScreen] and [RegisterScreen].
 *
 * @property Idle Initial state of the screen.
 * @property Loading state that represents the page loading.
 * @property Success state that appears when the screen finishes loading, in
 * addition to storing the user's userId and role.
 * @property Error state that appears when an error occurs during loading
 * and which carries the error message.
 */
sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val userId: String, val role: UserRole) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}
