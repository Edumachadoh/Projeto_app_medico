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

/*
* Viewmodel da tela de agenda do doutor
* Onde mostra todos o agendamento que o doutor tem
*/

@HiltViewModel
class AppointmentAgendaViewModel @Inject constructor(
    private val getDoctorAgendaAppointmentsUseCase: ListByDoctorUseCase,
    private val getPatientByIdUseCase: GetEntityByIdUseCase<Patient>,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    /*
    *   Guardado o estado da tela para que seja mudada de acordo
    *   Com o que for carregado na tela
    */
    private val _uiState = MutableStateFlow<AppointmentAgendaUiState>(AppointmentAgendaUiState.Loading)
    val uiState: StateFlow<AppointmentAgendaUiState> = _uiState.asStateFlow()

    /*
    *   Variável para guarfar o texto que está escrito
    *   no pesquisar
    */
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    //guardando os agendamentos que encontrou
    private var allAppointments = listOf<Appointment>()
    private val patientsMap = mutableMapOf<String, Patient>()

    //carregando agendamentos do doutor logado ao iniciar
    init {
        loadInitialAppointments()
    }

    //carregando todos os agendamentos guardados no banco
    private fun loadInitialAppointments() {
        //o usecase é assyncrono por isso uso esse comando
        viewModelScope.launch {
            _uiState.value = AppointmentAgendaUiState.Loading
            try {
                //pegando o id do doutor logado
                val doctorId = getCurrentUserIdUseCase.invoke()

                //retorna erro se caso o doutor for nulo
                if (doctorId == null) {
                    _uiState.value = AppointmentAgendaUiState.Error("Médico não autenticado")
                    return@launch
                }

                //guardando todos os agendamentos do doutor logado
                allAppointments = getDoctorAgendaAppointmentsUseCase.invoke(doctorId)

                /*
                    salvando as informações de
                    cada paciente em cada agendamento
                */
                allAppointments.forEach { appointment ->
                    val patientId = appointment.patientId
                    if (!patientsMap.containsKey(patientId)) {
                        getPatientByIdUseCase.invoke(patientId)?.let { patient ->
                            patientsMap[patientId] = patient
                        }
                    }
                }

                //mudando o estado da tela para sucesso caso os
                //agendamentos forem encontrados
                _uiState.value = AppointmentAgendaUiState.Success(
                    scheduledAppointments = allAppointments,
                    patients = patientsMap
                )
            } catch (e: Exception) {
                //retornando estado de erro caso haja algum erro
                _uiState.value = AppointmentAgendaUiState.Error(e.message ?: "Erro desconhecido ao carregar a agenda")
            }
        }
    }

    //comando para guardar dados da barra de pesquisa
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterAppointments(query) //executando comando de filtro
    }

    //filtrando os agendamentos de acordo com o nome do paciente
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