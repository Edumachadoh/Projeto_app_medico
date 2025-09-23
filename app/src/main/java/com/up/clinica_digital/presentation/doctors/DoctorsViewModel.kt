package com.up.clinica_digital.presentation.doctor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorsViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(DoctorUIState())
    val uiState = _uiState.asStateFlow()

    private val allDoctors = listOf(
        Doctor("1", "Dr. Dracula", "dracula@email.com", "111", "", "123456", "654321", "Hematologia", "SP"),
        Doctor("2", "Dr. Frankenstein", "frank@email.com", "222", "", "654321", "123456", "Clínica Geral", "SP"),
        Doctor("3", "Dr. Jekyll", "jekyll@email.com", "333", "", "987654", "456789", "Psiquiatria", "RJ"),
        Doctor("4", "Dr. House", "house@email.com", "444", "", "112233", "332211", "Nefrologia", "SP"),
        Doctor("5", "Dr. Estranho", "strange@email.com", "555", "", "554433", "334455", "Neurocirurgia", "EUA")
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

    fun SelectDoctor(doctor: Doctor){
        _uiState.update { it.copy(selectedDoctor = doctor)}
    }
}