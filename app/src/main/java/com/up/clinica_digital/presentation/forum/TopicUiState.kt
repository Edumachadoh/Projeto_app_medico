package com.up.clinica_digital.presentation.forum

import com.up.clinica_digital.domain.model.ForumTopic

// Defines a sealed interface to represent all
// * possible UI states for the Forum Topic screen (Loading, Success, Error)

sealed class TopicUiState {
    // State before loading the screen.
    data object Idle : TopicUiState()
    // Loading State.
    data object Loading : TopicUiState()
    // Sucesso State.
    data class Success(val topic: ForumTopic) : TopicUiState()
    // Erro State.
    data class Error(val message: String) : TopicUiState()
}