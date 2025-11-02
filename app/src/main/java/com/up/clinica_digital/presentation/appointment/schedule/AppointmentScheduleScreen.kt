package com.up.clinica_digital.presentation.appointment.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.up.clinica_digital.presentation.component.top_nav.CalendarTimeDatePicker
import com.up.clinica_digital.presentation.component.top_nav.DoctorInformation
import com.up.clinica_digital.presentation.component.top_nav.TopNavigationBar
import com.up.clinica_digital.presentation.navigation.Screen
import java.time.format.DateTimeFormatter

/**
 * PEDRO:
 * Screen where the patient selects the date and time to schedule
 * an appointment with a specific doctor.
 *
 * @param viewModel The ViewModel that manages the state of this screen.
 * @param navController Navigation controller to go to the confirmation screen.
 * @param doctorId The ID of the doctor selected on the previous screen.
 */
@Composable
fun AppointmentScheduleScreen(
    viewModel: AppointmentScheduleViewModel = hiltViewModel(),
    navController: NavHostController,
    doctorId: String,
) {
    //PEDRO: Screen state (starts as: Loading; Error; Success)
    val uiState by viewModel.uiState.collectAsState()
    //PEDRO: Formatting date to send to the database
    val formatter = remember { DateTimeFormatter.ofPattern("HH:mm") }

    //PEDRO: Loading the doctor selected on the list doctors screen
    LaunchedEffect(key1 = doctorId) {
        viewModel.loadDoctor(doctorId)
    }

    Scaffold(
        topBar = {
            //PEDRO: Navigation bar with back button
            TopNavigationBar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator()
                uiState.error != null -> Text(text = "Erro: ${uiState.error}", color = Color.Red)
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //PEDRO: Screen structure
                        Text(
                            text = "Agendamento",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        //PEDRO:
                        //  When the screen loads it will show the information
                        //  of the selected doctor
                        uiState.doctor?.let { doctor ->
                            DoctorInformation(doctor)
                            Spacer(modifier = Modifier.height(16.dp))
                            //PEDRO:
                            // Calendar component
                            // created to select
                            // date and time of the appointment
                            CalendarTimeDatePicker(
                                //PEDRO: It will register the date when changed
                                onDateTimeSelected = { dateTime ->
                                    viewModel.onDateTimeSelected(dateTime)
                                }
                            )
                            //PEDRO: It will show the selected time
                            //when it is modified
                            uiState.selectedDateTime?.let {
                                Text(text = "Horário Selecionado: ${it.format(formatter)}")
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            //PEDRO: Button that leads to the confirm appointment screen
                            Button(
                                onClick = {
                                    navController.navigate(
                                        Screen.ConfirmAppointment.createRoute(
                                            doctorId = uiState.doctor!!.id,
                                            dateTime = uiState.selectedDateTime
                                        )
                                    )
                                },
                                enabled = uiState.selectedDateTime != null
                            ) {
                                Text("Agendar")
                            }
                        } ?: run {
                            //PEDRO: If the doctor is not found
                            //it is not possible to schedule an appointment
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ){
                                Text("Médico não encontrado.")
                            }
                        }
                    }
                }
            }
        }
    }
}