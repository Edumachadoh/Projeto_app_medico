package com.up.clinica_digital.presentation.appointment.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Agendamento",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (uiState.error != null) {
                Text(text = "Erro: ${uiState.error}", color = Color.Red)
            } else if (uiState.appointmentScheduled) {
                Text("Consulta agendada com sucesso!")
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
                            uiState.selectedDateTime?.let { dateTime ->
                                navController.navigate(
                                    Screen.ConfirmAppointment.createRoute(
                                        doctorId = doctorId,
                                        dateTime = dateTime
                                    )
                                )
                            }
                        },
                        enabled = uiState.selectedDateTime != null
                    ) {
                        Text("Agendar")
                    }
                }
            }
        }
    }
}
