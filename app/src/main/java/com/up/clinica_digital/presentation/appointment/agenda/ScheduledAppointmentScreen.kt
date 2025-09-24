package com.up.clinica_digital.presentation.appointment.agenda

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.presentation.navigation.Screen
import com.up.clinica_digital.ui.theme.ClinicaDigitalTheme


@Composable
fun ScheduledAppointmentsScreen(
    navController: NavController,
    viewModel: ScheduledAppointmentViewModel = hiltViewModel(),
    onSelectAppointment: () -> Unit
    ) {
        val uiState by viewModel.scheduledAppointmentUiState.collectAsState()


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

                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items() { doctor ->
                            Button(onClick = {navController.navigate(Screen.DoctorDetails.createRoute(doctorId = doctor.id))}) {
                                DoctorItem(doctor = doctor)
                            }
                        }
                    }
                }
            }
        }

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
    }


    @Composable
    fun DoctorItem(doctor: Doctor) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = doctor.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(4.dp))
            Text(
                text = "CRM ${doctor.crm} RQE ${doctor.rqe}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Especialista em ${doctor.specialization}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
