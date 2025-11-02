package com.up.clinica_digital.presentation.appointment.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.up.clinica_digital.presentation.component.top_nav.DoctorInformation
import com.up.clinica_digital.presentation.component.bottom_nav.BottomNavItem
import com.up.clinica_digital.presentation.component.top_nav.TopNavigationBar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * PEDRO:
 * Appointment confirmation screen.
 *
 * This screen displays a summary of the appointment information (doctor, date, and time)
 * that the patient selected on the previous screen. It allows the patient
 * to confirm the appointment, which is then saved to the database.
 *
 * @param scheduleViewModel The ViewModel from the previous screen, reused here
 * to load the doctor's data.
 * @param confirmViewModel The ViewModel for this screen, responsible for
 * saving the appointment.
 * @param navController Navigation controller.
 * @param doctorId The doctor's ID (passed via navigation).
 * @param dateTime The selected date and time (passed via navigation as a String).
 */
@Composable
fun ConfirmAppointmentScreen(
    //PEDRO: Parameters passed from the previous screen AppointmentScheduleScreen
    scheduleViewModel: AppointmentScheduleViewModel = hiltViewModel(),
    confirmViewModel: ConfirmAppointmentViewModel = hiltViewModel(),
    navController: NavHostController,
    doctorId: String,
    dateTime: String
) {
    //PEDRO: Screen state
    val scheduleUiState by scheduleViewModel.uiState.collectAsState()
    val confirmUiState by confirmViewModel.uiState.collectAsState()

    //PEDRO: Date and time defined by the patient on the previous screen
    val parsedDateTime = LocalDateTime.parse(dateTime)

    //PEDRO: Loading doctor
    LaunchedEffect(key1 = doctorId) {
        scheduleViewModel.loadDoctor(doctorId)
    }

    Scaffold(
        topBar = {
            //PEDRO: Top bar with option to go back
            TopNavigationBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                confirmUiState.isLoading || scheduleUiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                confirmUiState.error != null -> Text("Erro: ${confirmUiState.error}", color = Color.Red)
                scheduleUiState.error != null -> Text("Erro: ${scheduleUiState.error}", color = Color.Red)
                confirmUiState.appointmentScheduled -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        //PEDRO:
                        //Information defined by the patient on the previous screen
                        //that will appear when they confirm the appointment
                        //so they know whether or not to confirm the appointment
                        Text("Consulta agendada com sucesso!", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            navController.navigate(BottomNavItem.Consultas.route) {
                                popUpTo(BottomNavItem.Medicos.route)
                            }
                        }) {
                            Text("Ver Minhas Consultas")
                        }
                    }
                }
                else -> {
                    //PEDRO:
                    //Information from the previous screen for the patient
                    //to decide whether or not to schedule
                    Text(
                        text = "Confirmar Agendamento",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                    //PEDRO: Doctor information
                    scheduleUiState.doctor?.let { doctor ->
                        DoctorInformation(doctor = doctor)
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Data e Hora:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        //PEDRO: Appointment date and time
                        Text(
                            text = parsedDateTime.format(
                                DateTimeFormatter.ofLocalizedDateTime(
                                    FormatStyle.FULL,
                                    FormatStyle.SHORT
                                )
                            ),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        //PEDRO: Button to confirm appointment
                        Button(
                            onClick = {
                                confirmViewModel.scheduleAppointment(
                                    doctorId = doctorId,
                                    dateTime = parsedDateTime
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Confirmar")
                        }
                        //PEDRO:
                        // If doctor information is not found
                        // it is not possible to schedule an appointment
                    } ?: Text("Médico não encontrado.")
                }
            }
        }
    }
}