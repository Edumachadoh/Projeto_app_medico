package com.up.clinica_digital.presentation.appointment.patient

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import com.up.clinica_digital.domain.usecase.appointment.ListByPatientUseCase
import com.up.clinica_digital.domain.usecase.user.GetCurrentUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * PEDRO:
 * ViewModel for the patient's scheduled appointments screen [ScheduledAppointmentsScreen].
 *
 * Responsible for loading all appointments for the logged-in patient,
 * fetching the associated doctors' data, and filtering the list
 * based on the user's search.
 *
 * @param getPatientScheduledAppointmentsUseCase Use case to list appointments by patient.
 * @param getDoctorByIdUseCase Use case to fetch a doctor by ID.
 * @param getCurrentUserIdUseCase Use case to get the ID of the logged-in user.
 */

@HiltViewModel
class ScheduledAppointmentViewModel @Inject constructor(
    private val getPatientScheduledAppointmentsUseCase: ListByPatientUseCase,
    private val getDoctorByIdUseCase: GetEntityByIdUseCase<Doctor>,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {
    /*
    PEDRO:
    * Stores the state of the screen so it can be changed according
    * to what is loaded on the screen
    */
    private val _uiState = MutableStateFlow<ScheduledAppointmentUiState>(ScheduledAppointmentUiState.Loading)
    val uiState: StateFlow<ScheduledAppointmentUiState> = _uiState.asStateFlow()

    /*
    PEDRO:
    * Variable to store the text that is written
    * in the search bar
    */
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    //PEDRO: Storing the scheduled appointments that the use case found
    private var allAppointments = listOf<Appointment>()
    //PEDRO: Map of found doctors
    private val doctorsMap = mutableMapOf<String, Doctor>()

    //PEDRO: Loading all appointments on init
    init {
        loadInitialAppointments()
    }

    /**
     * PEDRO:
     * Loads the initial list of appointments for the logged-in patient
     * and the associated doctors' data.
     */
    private fun loadInitialAppointments() {
        viewModelScope.launch {
            _uiState.value = ScheduledAppointmentUiState.Loading
            try {
                val patientId = getCurrentUserIdUseCase.invoke()

                if (patientId == null) {
                    _uiState.value = ScheduledAppointmentUiState.Error("Usuário não autenticado")
                    return@launch
                }

                allAppointments = getPatientScheduledAppointmentsUseCase.invoke(patientId)

                allAppointments.map { it.doctorId }.distinct().forEach { doctorId ->
                    if (!doctorsMap.containsKey(doctorId)) {
                        getDoctorByIdUseCase.invoke(doctorId)?.let { doctor ->
                            doctorsMap[doctorId] = doctor
                        }
                    }
                }

                _uiState.value = ScheduledAppointmentUiState.Success(
                    scheduledAppointments = allAppointments,
                    doctors = doctorsMap
                )
            } catch (e: Exception) {
                _uiState.value = ScheduledAppointmentUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    /**
     * PEDRO:
     * Called when the text in the search bar is changed.
     * Updates the [searchQuery] and triggers the list filtering.
     *
     * @param query The new search text.
     */
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterAppointments(query) //executing filter function
    }

    //PEDRO: Filtering appointments according to the doctor's name
    private fun filterAppointments(query: String) {
        val filteredList = if (query.isBlank()) {
            allAppointments
        } else {
            allAppointments.filter { appointment ->
                val doctor = doctorsMap[appointment.doctorId]
                doctor?.name?.contains(query, ignoreCase = true) == true
            }
        }
        _uiState.value = ScheduledAppointmentUiState.Success(
            scheduledAppointments = filteredList,
            doctors = doctorsMap
        )
    }
}