package com.up.clinica_digital.presentation.doctors

import com.up.clinica_digital.domain.model.Doctor

/**
 * Representa o estado da interface de usuário (UI) para as telas
 * relacionadas a médicos (DoctorsListScreen e DoctorDetailsScreen).
 *
 * @property searchQuery O texto atual na barra de pesquisa,
 * usado para filtrar a lista de médicos.
 * @property isLoading Indica se uma operação de carregamento
 * (seja da lista ou de um médico) está em andamento.
 * @property doctors A lista de médicos a ser exibida
 * (pode ser a lista completa ou a filtrada).
 * @property doctor O médico específico em que os detalhes estão sendo exibidos
 * (usado na tela de detalhes).
 * @property error Contém uma mensagem de erro, caso alguma operação falhe.
 */
data class DoctorUIState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val doctors: List<Doctor> = emptyList(),
    val doctor: Doctor? = null,
    val error: String? = null
)