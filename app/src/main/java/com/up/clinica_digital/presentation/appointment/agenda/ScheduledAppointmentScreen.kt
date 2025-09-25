package com.up.clinica_digital.presentation.appointment.agenda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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

@Composable
fun ScheduledAppointmentsScreen(
    navController: NavController,
    viewModel: ScheduledAppointmentViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = viewModel.searchQuery.value,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Nome do Médico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))
            Text("Consultas", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            when (val state = uiState) {
                is ScheduledAppointmentUiState.Loading -> CircularProgressIndicator()
                is ScheduledAppointmentUiState.Error -> {
                    Text(state.message, color = Color.Red)
                }
                is ScheduledAppointmentUiState.Success -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.scheduledAppointments) { appointment ->
                            val doctor = state.doctors[appointment.doctorId]
                            if (doctor != null) {
                                Button(onClick = {
                                    navController.navigate()
                                }) {
                                    AppointmentItem(appointment = appointment, doctor = doctor)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}