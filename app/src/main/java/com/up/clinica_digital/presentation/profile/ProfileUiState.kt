package com.up.clinica_digital.presentation.profile

import com.up.clinica_digital.domain.model.User

/**
 * Represents the possible states of the user interface (UI)
 * for the [ProfileScreen].
 */
sealed class ProfileUiState {
    object Idle : ProfileUiState()
    object Loading : ProfileUiState()
    data class Success(var user: User) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}