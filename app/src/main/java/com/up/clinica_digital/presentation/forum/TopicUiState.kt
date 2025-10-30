package com.up.clinica_digital.presentation.forum

import com.up.clinica_digital.domain.model.ForumTopic


// Define uma interface 'selada' para representar todos os
// * estados possíveis da UI da tela do Topico de Fórum (Loading, Success, Error)
sealed class TopicUiState {
    // Estado Ocioso (antes de qualquer carregamento).
    data object Idle : TopicUiState()
    // Estado de Loading.
    data object Loading : TopicUiState()
    // Estado de Sucesso.
    data class Success(val topic: ForumTopic) : TopicUiState()
    // Estado de Erro.
    data class Error(val message: String) : TopicUiState()
}