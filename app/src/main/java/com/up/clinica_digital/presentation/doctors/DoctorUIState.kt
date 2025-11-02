package com.up.clinica_digital.presentation.doctors

import com.up.clinica_digital.domain.model.Doctor

/**
 * Represents the user interface (UI) state for the
 * screens related to doctors (DoctorsListScreen and DoctorDetailsScreen).
 *
 * @property searchQuery The current text in the search bar,
 * used to filter the list of doctors.
 * @property isLoading Indicates if a loading operation
 * (either of the list or a doctor) is in progress.
 * @property doctors The list of doctors to be displayed
 * (can be the full list or the filtered one).
 * @property doctor The specific doctor whose details are being displayed
 * (used in the details screen).
 * @property error Contains an error message, in case any operation fails.
 */
data class DoctorUIState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val doctors: List<Doctor> = emptyList(),
    val doctor: Doctor? = null,
    val error: String? = null
)