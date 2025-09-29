package com.up.clinica_digital.presentation.appointment.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.up.clinica_digital.presentation.appointment.components.CalendarTimeDatePicker
import com.up.clinica_digital.presentation.appointment.components.DoctorInformation
import com.up.clinica_digital.presentation.component.top_nav.TopNavigationBar
import com.up.clinica_digital.presentation.navigation.Screen

@Composable
fun AppointmentScheduleScreen(
    viewModel: AppointmentScheduleViewModel = hiltViewModel(),
    navController: NavHostController,
    doctorId: String,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = doctorId) {
        viewModel.loadDoctor(doctorId)
    }

    Scaffold(
        topBar = {
            TopNavigationBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Agendamento",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (uiState.error != null) {
                Text(text = "Erro: ${uiState.error}", color = Color.Red)
            } else if (uiState.appointmentScheduled) {
                Text("Consulta agendada com sucesso!")
                Button(onClick = {
                    // Navega para a tela de consultas e limpa a pilha de volta
                    navController.navigate(Screen.Home.createRoute(com.up.clinica_digital.domain.model.UserRole.PATIENT)) {
                        popUpTo(Screen.Home.createRoute(com.up.clinica_digital.domain.model.UserRole.PATIENT)) {
                            inclusive = true
                        }
                    }
                }) {
                    Text("Ver minhas consultas")
                }
            } else {
                uiState.doctor?.let { doctor ->
                    DoctorInformation(doctor)
                    Spacer(modifier = Modifier.height(16.dp))
                    CalendarTimeDatePicker(
                        onDateTimeSelected = { dateTime ->
                            viewModel.onDateTimeSelected(dateTime)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {

                        },
                        enabled = uiState.selectedDateTime != null
                    ) {
                        Text("Agendar")
                    }
                } ?: run {
                    Text("Médico não encontrado.")
                }
            }
        }
    }
}