package com.up.clinica_digital.presentation.appointment.doctor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.up.clinica_digital.presentation.navigation.Screen

/**
 * PEDRO:
 * Screen that shows the appointment agenda for the logged-in doctor.
 * Allows filtering appointments by patient name.
 * Uses [AppointmentAgendaViewModel] to manage state and
 * fetch data.
 *
 * @param navController Navigation controller (used to go to details).
 * @param viewModel Screen's ViewModel, injected by Hilt.
 */
@Composable
fun AppointmentsAgendaScreen(
    navController: NavController,
    viewModel: AppointmentAgendaViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    //PEDRO: structure of the logged-in doctor's agenda screen
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            //PEDRO: Search bar by patient name
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Nome do Paciente") },
                modifier = Modifier.fillMaxWidth()
            )

            //PEDRO: Place where all appointments for the logged-in doctor are shown
            Spacer(Modifier.height(16.dp))
            Text("Minha Agenda", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            //PEDRO: If successful it will show the entire agenda
            //otherwise it will just show a message "No appointments found"
            when (val state = uiState) {
                is AppointmentAgendaUiState.Loading -> CircularProgressIndicator()
                is AppointmentAgendaUiState.Error -> {
                    Text(state.message, color = Color.Red)
                }
                //PEDRO: If the state is Success, displays the list of appointments
                is AppointmentAgendaUiState.Success -> {
                    if (state.scheduledAppointments.isEmpty()) {
                        Text("Nenhuma consulta agendada.")
                    }
                    //PEDRO: Scrollable list of appointments
                    else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(state.scheduledAppointments) { appointment ->
                                val patient = state.patients[appointment.patientId]
                                AgendaItem(
                                    appointment = appointment,
                                    patient = patient,
                                    onAppointmentClick = {
                                        navController.navigate(
                                            Screen.AgendaDetails.createRoute(appointment.id)
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}