package com.up.clinica_digital.presentation.auth

sealed class CrmUiState {
    object Idle : CrmUiState()
    object Loading : CrmUiState()
    data class Success(val doctors: List<com.up.clinica_digital.domain.model.CfmDoctor>) : CrmUiState()
    data class Error(val message: String) : CrmUiState()
}