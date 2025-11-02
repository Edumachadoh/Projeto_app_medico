package com.up.clinica_digital.presentation.appointment.doctor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Patient
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import com.up.clinica_digital.domain.usecase.appointment.ListByDoctorUseCase
import com.up.clinica_digital.domain.usecase.user.GetCurrentUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * PEDRO:
 * ViewModel for the doctor's agenda screen [AppointmentsAgendaScreen].
 *
 * Responsible for loading the appointments for the logged-in doctor,
 * fetching the associated patient data, and filtering the list
 * based on the user's search.
 *
 * @param getDoctorAgendaAppointmentsUseCase Use case to list appointments by doctor.
 * @param getPatientByIdUseCase Use case to fetch a patient by ID.
 * @param getCurrentUserIdUseCase Use case to get the ID of the logged-in user.
 */

@HiltViewModel
class AppointmentAgendaViewModel @Inject constructor(
    private val getDoctorAgendaAppointmentsUseCase: ListByDoctorUseCase,
    private val getPatientByIdUseCase: GetEntityByIdUseCase<Patient>,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    /*
    PEDRO:
    * Stores the state of the screen so it can be changed according
    * to what is loaded on the screen
    */
    private val _uiState = MutableStateFlow<AppointmentAgendaUiState>(AppointmentAgendaUiState.Loading)
    val uiState: StateFlow<AppointmentAgendaUiState> = _uiState.asStateFlow()

    /*
    PEDRO:
    * Variable to store the text that is written
    * in the search bar
    */
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    //PEDRO: storing the appointments it found
    private var allAppointments = listOf<Appointment>()
    private val patientsMap = mutableMapOf<String, Patient>()

    //PEDRO: loading appointments for the logged-in doctor on initialization
    init {
        loadInitialAppointments()
    }

    /**
     * PEDRO:
     * Loads the initial list of appointments for the logged-in doctor.
     *
     * This method fetches the doctor's ID, then fetches their appointments,
     * and finally fetches the data for each associated patient.
     * Updates the [_uiState] to [AppointmentAgendaUiState.Success] or
     * [AppointmentAgendaUiState.Error] upon completion.
     */
    private fun loadInitialAppointments() {
        //PEDRO: the use case is asynchronous, that's why I use this command
        viewModelScope.launch {
            _uiState.value = AppointmentAgendaUiState.Loading
            try {
                //PEDRO: getting the logged-in doctor's id
                val doctorId = getCurrentUserIdUseCase.invoke()

                //PEDRO: returns an error if the doctor is null
                if (doctorId == null) {
                    _uiState.value = AppointmentAgendaUiState.Error("Médico não autenticado")
                    return@launch
                }

                //PEDRO: storing all appointments for the logged-in doctor
                allAppointments = getDoctorAgendaAppointmentsUseCase.invoke(doctorId)

                /*
                PEDRO:
                    saving the information
                    for each patient in each appointment
                */
                allAppointments.forEach { appointment ->
                    val patientId = appointment.patientId
                    if (!patientsMap.containsKey(patientId)) {
                        getPatientByIdUseCase.invoke(patientId)?.let { patient ->
                            patientsMap[patientId] = patient
                        }
                    }
                }

                //PEDRO: changing the screen state to success if the
                //appointments were found
                _uiState.value = AppointmentAgendaUiState.Success(
                    scheduledAppointments = allAppointments,
                    patients = patientsMap
                )
            } catch (e: Exception) {
                //PEDRO: returning error state if there is any error
                _uiState.value = AppointmentAgendaUiState.Error(e.message ?: "Erro desconhecido ao carregar a agenda")
            }
        }
    }

    /**
     * PEDRO:
     * Called when the text in the search bar is changed.
     * Updates the [_searchQuery] and triggers the list filtering.
     *
     * @param query The new search text.
     */
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterAppointments(query) //executing filter function
    }

    //PEDRO: filtering appointments according to the patient's name
    private fun filterAppointments(query: String) {
        val filteredList = if (query.isBlank()) {
            allAppointments
        } else {
            allAppointments.filter { appointment ->
                val patient = patientsMap[appointment.patientId]
                patient?.name?.contains(query, ignoreCase = true) == true
            }
        }
        if (_uiState.value is AppointmentAgendaUiState.Success){
            _uiState.update {
                (it as AppointmentAgendaUiState.Success).copy(scheduledAppointments = filteredList)
            }
        }
    }
}