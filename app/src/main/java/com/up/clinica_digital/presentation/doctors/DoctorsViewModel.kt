package com.up.clinica_digital.presentation.doctors

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import com.up.clinica_digital.domain.usecase.ListEntitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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
            try {
                val doctor = getDoctorUseCase.invoke(doctorId)
                _uiState.update { it.copy(doctor = doctor, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Erro desconhecido", isLoading = false) }
            }
        }
    }

}