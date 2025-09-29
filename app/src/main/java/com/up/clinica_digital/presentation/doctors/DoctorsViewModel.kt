package com.up.clinica_digital.presentation.doctors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorsViewModel @Inject constructor(
    private val getDoctorUseCase: GetEntityByIdUseCase<Doctor>
) : ViewModel() {

    private val _uiState = MutableStateFlow(DoctorUIState())
    val uiState = _uiState.asStateFlow()

    private val allDoctors = listOf(
        Doctor("1", "Dr. Saturnino", "saturnino@email.com", "111", "", "123456", "654321", "Cirurgia Bariátrica", "SP"),
        Doctor("2", "Dr. Drauzio", "drauzio@email.com", "222", "", "654321", "123456", "Oncologia", "SP"),
        Doctor("3", "Dra. Gabriele", "bi@email.com", "333", "", "987654", "456789", "Cirurgia Plástica", "RJ"),
        Doctor("4", "Dr. Luis", "luis@email.com", "444", "", "112233", "332211", "Clínica Geral", "SP"),
        Doctor("5", "Dr. Gregory", "gregory@email.com", "555", "", "554433", "334455", "Nefrologia", "EUA")
    )

    init {
        _uiState.update { it.copy(doctors = allDoctors) }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        filterDoctors(query)
    }

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

    fun loadDoctor(doctorId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            /*try {
                val doctor = getDoctorUseCase.invoke(doctorId)
                _uiState.update { it.copy(doctor = doctor, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Erro desconhecido", isLoading = false) }
            }*/

            val doctor = allDoctors.find { it.id == doctorId }

            _uiState.update { it.copy(isLoading = false, doctor = doctor) }
        }
    }

}