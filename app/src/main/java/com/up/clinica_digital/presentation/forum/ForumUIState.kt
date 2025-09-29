package com.up.clinica_digital.presentation.forum

import com.up.clinica_digital.domain.model.ForumTopic

sealed interface ForumUiState {
    data object Loading : ForumUiState
    data class Success(val topics: List<ForumTopic>) : ForumUiState
    data class Error(val message: String) : ForumUiState
}