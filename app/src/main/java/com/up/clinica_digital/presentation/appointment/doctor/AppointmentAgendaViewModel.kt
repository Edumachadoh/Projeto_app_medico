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
 * ViewModel para a tela de agenda do médico [AppointmentsAgendaScreen].
 *
 * Responsável por carregar os agendamentos do médico logado,
 * buscar os dados dos pacientes associados e filtrar a lista
 * com base na pesquisa do usuário.
 *
 * @param getDoctorAgendaAppointmentsUseCase Caso de uso para listar agendamentos por médico.
 * @param getPatientByIdUseCase Caso de uso para buscar um paciente pelo ID.
 * @param getCurrentUserIdUseCase Caso de uso para obter o ID do usuário logado.
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

    /**
     * Carrega a lista inicial de agendamentos do médico logado.
     *
     * Este método busca o ID do médico, em seguida busca seus agendamentos
     * e, por fim, busca os dados de cada paciente associado.
     * Atualiza o [_uiState] para [AppointmentAgendaUiState.Success] ou
     * [AppointmentAgendaUiState.Error] ao concluir.
     */
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

    /**
     * Chamado quando o texto na barra de pesquisa é alterado.
     * Atualiza o [_searchQuery] e aciona a filtragem da lista.
     *
     * @param query O novo texto da pesquisa.
     */
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterAppointments(query) //executando função de filtro
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