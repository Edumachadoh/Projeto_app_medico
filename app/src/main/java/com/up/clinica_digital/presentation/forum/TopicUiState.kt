package com.up.clinica_digital.presentation.forum

import com.up.clinica_digital.domain.model.ForumTopic


sealed class TopicUiState {
    data object Idle : TopicUiState()
    data object Loading : TopicUiState()
    data class Success(val topic: ForumTopic) : TopicUiState()
    data class Error(val message: String) : TopicUiState()
}