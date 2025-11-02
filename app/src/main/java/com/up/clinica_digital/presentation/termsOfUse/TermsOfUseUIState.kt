package com.up.clinica_digital.presentation.termsOfUse

/**
 * Represents the states of the terms of use interface (UI).
 *
 * @property Success state that stores if the screen
 * was loaded successfully.
 */
sealed interface TermsOfUseUiState {
    data class Success(
        val title: String,
        val content: String
    ) : TermsOfUseUiState
}