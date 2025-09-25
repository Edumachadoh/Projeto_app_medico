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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.up.clinica_digital.domain.model.Doctor


@Composable
fun ScheduledAppointmentsScreen(
    navController: NavController,
    viewModel: ScheduledAppointmentViewModel = hiltViewModel(),
    onSelectAppointment: () -> Unit
) {
    val uiState by viewModel.scheduledAppointmentUiState.collectAsState()

    when (uiState) {
        is ScheduledAppointmentUiState.Idle -> Unit
        is ScheduledAppointmentUiState.Loading -> CircularProgressIndicator()
        is ScheduledAppointmentUiState.Error -> {
            val message = (uiState as ScheduledAppointmentUiState.Error).message
            Text(message, color = Color.Red)
        }

        is ScheduledAppointmentUiState.Success -> {
            val successState = uiState as ScheduledAppointmentUiState.Success
            LaunchedEffect(successState.scheduledAppointments) {
                onSelectAppointment()
            }
        }
    }
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

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(viewModel.allScheduledAppointments) { appointment ->
                            Button(onClick = {}) {
                                AppointmentItem(appointment = appointment, doctor = viewModel.getDoctorById(appointment.doctorId))
                            }
                        }
                    }
                }
        }
    }
}
