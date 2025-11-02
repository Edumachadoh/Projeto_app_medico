package com.up.clinica_digital.presentation.appointment.patient

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
 * Displays the list of scheduled appointments for the logged-in patient.
 *
 * This screen allows the patient to see their future appointments and
 * filter the list by the doctor's name.
 *
 * @param navController Navigation controller to open appointment details.
 * @param viewModel The ViewModel that manages the state and logic of this screen.
 */
@Composable
fun ScheduledAppointmentsScreen(
    navController: NavController,
    viewModel: ScheduledAppointmentViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    //PEDRO: Structure of the logged-in patient's appointments screen
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            //PEDRO: Search bar by doctor's name
            OutlinedTextField(
                value = viewModel.searchQuery.value,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Nome do Médico") },
                modifier = Modifier.fillMaxWidth()
            )

            //PEDRO: Place where all appointments for the logged-in patient are shown
            Spacer(Modifier.height(16.dp))
            Text("Consultas", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            //PEDRO: If successful, it will show all appointments
            //otherwise, it will just show a message "No appointments found"
            when (val state = uiState) {
                is ScheduledAppointmentUiState.Loading -> CircularProgressIndicator()
                is ScheduledAppointmentUiState.Error -> {
                    Text(state.message, color = Color.Red)
                }

                is ScheduledAppointmentUiState.Success -> {
                    if (state.scheduledAppointments.isNotEmpty()) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            items(state.scheduledAppointments) { appointment ->
                                val doctor = state.doctors[appointment.doctorId]
                                if (doctor != null) {
                                    AppointmentItem(
                                        appointment = appointment, doctor = doctor,
                                        onAppointmentClick = {
                                            navController.navigate(
                                                Screen.AppointmentDetails.createRoute(appointment.id)
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
}