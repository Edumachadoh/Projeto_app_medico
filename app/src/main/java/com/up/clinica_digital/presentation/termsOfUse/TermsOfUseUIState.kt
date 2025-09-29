package com.up.clinica_digital.presentation.termsOfUse

sealed interface TermsOfUseUiState {
    data class Success(
        val title: String,
        val content: String
    ) : TermsOfUseUiState
}