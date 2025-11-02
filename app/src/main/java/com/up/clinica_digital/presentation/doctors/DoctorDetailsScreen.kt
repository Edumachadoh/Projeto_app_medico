package com.up.clinica_digital.presentation.doctors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.up.clinica_digital.presentation.component.top_nav.TopNavigationBar
import com.up.clinica_digital.presentation.navigation.Screen

/**
 * PEDRO:
 * Doctor details screen, selected
 * by the logged-in patient from the doctors list.
 *
 * @param navController Parameter that allows the
 * patient to navigate from details to schedule an appointment.
 * @param viewModel ViewModel that loads doctor information.
 * @param doctorId ID of the doctor selected on the [DoctorsListScreen].
 */

@Composable
fun DoctorDetailsScreen(
    navController: NavController,
    viewModel: DoctorsViewModel = hiltViewModel(),
    doctorId: String,
) {
    // screen UI state
    val uiState by viewModel.uiState.collectAsState()

    // loading doctor information when the screen starts
    LaunchedEffect(key1 = doctorId) {
        viewModel.loadDoctor(doctorId)
    }

    Scaffold(
        // top navigation bar to go back
        // to the previous screen
        topBar = { TopNavigationBar(navController) },
        bottomBar = {
            BottomAppBar {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center){
                    // Button to take the logged-in patient to the
                    // screen to schedule an appointment with this selected doctor
                    Button(onClick = { navController.navigate(Screen.Appointment.createRoute(doctorId))
                    })
                    {
                        Text("Agendar consulta")
                    }
                }
            }
        })

    { innerPadding ->

        // doctor's information
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = uiState.doctor?.name?: "Erro ao encontrar nome",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = uiState.doctor?.specialization?: "Erro ao encontrar especialização",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = "CRM:${uiState.doctor?.crm?: "Erro ao encontrar crm"}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "RQE:${uiState.doctor?.rqe?: "Erro ao encontrar rqe"}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}