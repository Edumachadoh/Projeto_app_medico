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
 * ViewModel for screens related to Doctors (DoctorsListScreen and DoctorDetailsScreen).
 *
 * This class manages the UI state for:
 * 1. Loading and displaying the complete list of doctors.
 * 2. Filtering the list of doctors (by specialty).
 * 3. Loading the data of an individual doctor for the details screen.
 *
 * @param getDoctorUseCase Use case to fetch a specific doctor by ID.
 * @param getAllDoctorsUseCase Use case to list all doctor entities.
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
     * Called when the text in the search bar is changed.
     * Updates the [DoctorUIState.searchQuery] and triggers the list filtering.
     *
     * @param query The new search text.
     */
    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        filterDoctors(query) //-- > sending to the [filterDoctors] function
    }

    /**
     * Filters the [allDoctors] list based on the doctor's specialty.
     * Updates the [DoctorUIState.doctors] with the filtered list.
     *
     * @param query The text (specialty) used for filtering.
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
     * Loads the data of a specific doctor by ID.
     * (Used by the doctor details screen).
     *
     * @param doctorId The ID of the doctor to be loaded.
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