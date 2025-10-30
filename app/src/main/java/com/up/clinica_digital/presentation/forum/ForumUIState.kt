package com.up.clinica_digital.presentation.forum

import com.up.clinica_digital.domain.model.ForumTopic

// Define uma interface 'selada' para representar todos os
// * estados possíveis da UI da tela do Fórum (Loading, Success, Error)
sealed interface ForumUiState {
    // Estado de Loading.
    data object Loading : ForumUiState
    // Estado de Sucesso.
    data class Success(val topics: List<ForumTopic>) : ForumUiState
    // Estado de Erro.
    data class Error(val message: String) : ForumUiState
}