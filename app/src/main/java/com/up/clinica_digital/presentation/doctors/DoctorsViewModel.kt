package com.up.clinica_digital.presentation.doctors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import com.up.clinica_digital.domain.usecase.ListEntitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para as telas relacionadas a Médicos (DoctorsListScreen e DoctorDetailsScreen).
 *
 * Esta classe gerencia o estado da UI para:
 * 1. Carregar e exibir a lista completa de médicos.
 * 2. Filtrar a lista de médicos (pela especialidade).
 * 3. Carregar os dados de um médico individual para a tela de detalhes.
 *
 * @param getDoctorUseCase Caso de uso para buscar um médico específico pelo ID.
 * @param getAllDoctorsUseCase Caso de uso para listar todas as entidades de médicos.
 */
@HiltViewModel
class DoctorsViewModel @Inject constructor(
    private val getDoctorUseCase: GetEntityByIdUseCase<Doctor>,
    private val getAllDoctorsUseCase: ListEntitiesUseCase<Doctor>
) : ViewModel() {

    private val _uiState = MutableStateFlow(DoctorUIState())
    val uiState = _uiState.asStateFlow()

    private var allDoctors = listOf<Doctor>()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(doctors = getAllDoctorsUseCase.invoke()) }
            allDoctors = _uiState.value.doctors
        }
    }

    /**
     * Chamado quando o texto na barra de pesquisa é alterado.
     * Atualiza o [DoctorUIState.searchQuery] e aciona a filtragem da lista.
     *
     * @param query O novo texto da pesquisa.
     */
    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        filterDoctors(query) /**--> mandando para a função [filterDoctors]*/
    }

    /**
     * Filtra a lista de [allDoctors] com base na especialidade do médico.
     * Atualiza o [DoctorUIState.doctors] com a lista filtrada.
     *
     * @param query O texto (especialidade) usado para filtrar.
     */
    private fun filterDoctors(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val filteredList = if (query.isBlank()) {
                allDoctors
            } else {
                allDoctors.filter { doctor ->
                    doctor.specialization.contains(query, ignoreCase = true)
                }
            }

            _uiState.update { it.copy(isLoading = false, doctors = filteredList) }
        }
    }

    /**
     * Carrega os dados de um médico específico pelo ID.
     * (Usado pela tela de detalhes do médico).
     *
     * @param doctorId O ID do médico a ser carregado.
     */
    fun loadDoctor(doctorId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val doctor = getDoctorUseCase.invoke(doctorId)
                _uiState.update { it.copy(doctor = doctor, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Erro desconhecido", isLoading = false) }
            }
        }
    }

}