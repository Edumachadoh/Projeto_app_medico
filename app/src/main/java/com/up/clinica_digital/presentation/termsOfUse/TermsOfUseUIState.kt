package com.up.clinica_digital.presentation.termsOfUse

import com.up.clinica_digital.domain.model.Doctor
import java.time.LocalDateTime

/**
 * Representa os estados da interface de termos de uso (UI).
 *
 * @property Success estado que guarda se caso a tela
 * foi carregada com sucesso.

 */
sealed interface TermsOfUseUiState {
    data class Success(
        val title: String,
        val content: String
    ) : TermsOfUseUiState
}