package com.up.clinica_digital.presentation.appointment.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.up.clinica_digital.presentation.appointment.components.DoctorInformation
import com.up.clinica_digital.presentation.component.top_nav.TopNavigationBar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ConfirmAppointmentScreen(
    navController: NavHostController,
    viewModel: AppointmentViewModel = hiltViewModel(),
    doctorId: String,
    patientId: String,
    dateTime: String
) {
    val uiState by viewModel.uiState.collectAsState()
    val parsedDateTime = LocalDateTime.parse(dateTime)

    LaunchedEffect(key1 = doctorId, key2 = parsedDateTime) {
        viewModel.loadDoctor(doctorId)
        viewModel.onDateTimeSelected(parsedDateTime)
    }

    Scaffold(
        topBar = { TopNavigationBar(navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {

            Text(
                text = "Confirmar Agendamento",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                uiState.error != null -> {
                    Text(text = "Erro: ${uiState.error}", color = Color.Red)
                }
                uiState.appointmentScheduled -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Consulta agendada com sucesso!", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { navController.popBackStack() }) { // Ou navegar para a tela de "Minhas Consultas"
                            Text("Voltar")
                        }
                    }
                }
                uiState.doctor != null -> {
                    Column(modifier = Modifier.weight(1f)) {
                        DoctorInformation(doctorInfo = uiState.doctor!!)
                        Spacer(modifier = Modifier.height(32.dp))

                        // Exibe a data e hora formatadas
                        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

                        Text(
                            text = "Data: ${parsedDateTime.format(dateFormatter)}",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Hora: ${parsedDateTime.format(timeFormatter)}",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
                        )
                    }

                    // Botões na parte inferior
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Voltar")
                        }
                        Button(
                            onClick = { viewModel.scheduleAppointment(patientId) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Agendar")
                        }
                    }
                }
            }
        }
    }
}
