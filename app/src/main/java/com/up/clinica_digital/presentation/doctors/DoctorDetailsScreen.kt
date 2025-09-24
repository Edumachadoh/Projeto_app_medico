package com.up.clinica_digital.presentation.doctors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.up.clinica_digital.presentation.component.top_nav.TopNavigationBar
import com.up.clinica_digital.presentation.doctor.DoctorsViewModel
import com.up.clinica_digital.presentation.navigation.Screen

@Composable
fun DoctorDetailsScreen(
    navController: NavController,
    viewModel: DoctorsViewModel = hiltViewModel(),
    doctorId: String,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = doctorId) {
        viewModel.loadDoctor(doctorId)
    }

    Scaffold(topBar = { TopNavigationBar(navController) })
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = uiState.doctor?.name ?: "")
            Text(text = uiState.doctor?.specialization?: "")
            Text(text = uiState.doctor?.crm?: "")

            TextButton(onClick = { navController.navigate(Screen.Appointment.createRoute("1",doctorId))}) {
                Text("Agendar consulta")
            }
        }
    }
}
