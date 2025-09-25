package com.up.clinica_digital.presentation.appointment.details

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.up.clinica_digital.presentation.appointment.agenda.AppointmentItem
import com.up.clinica_digital.presentation.component.top_nav.TopNavigationBar

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
                        modifier = Modifier.fillMaxSize()
                    ) {
                        AppointmentItem(
                            appointment = state.appointment,
                            doctor = state.doctor
                        )
                        Spacer(modifier = Modifier.height(32.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Informações adicionais, se houver.",
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
                                        navController.popBackStack() //não ta funcionando ainda
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
