package com.up.clinica_digital.presentation.appointment.patient.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.up.clinica_digital.presentation.component.top_nav.TopNavigationBar
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@Composable
fun AppointmentDetailsScreen(
    navController: NavHostController,
    viewModel: AppointmentDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopNavigationBar(navController = navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is AppointmentDetailsUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is AppointmentDetailsUiState.Error -> {
                    Text(text = "Erro: ${state.message}", color = Color.Red)
                }
                is AppointmentDetailsUiState.Success -> {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = state.doctor.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Especialidade: ${state.doctor.specialization}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Data: ${formatDate(state.appointment.scheduledAt)}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Hora: ${formatTime(state.appointment.scheduledAt)}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = state.appointment.status.toString(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Button(
                                onClick = {
                                    viewModel.cancelAppointment {
                                        navController.popBackStack()
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Cancelar Consulta")
                            }
                        }
                    }
                }
            }
        }
    }
}
private fun formatDate(date: LocalDateTime): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()))
}

private fun formatTime(date: LocalDateTime): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()))
}