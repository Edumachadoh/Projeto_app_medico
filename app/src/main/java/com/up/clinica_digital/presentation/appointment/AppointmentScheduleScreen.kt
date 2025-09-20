package com.up.clinica_digital.presentation.appointment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.presentation.appointment.components.CalendarTimeDatePicker
import com.up.clinica_digital.presentation.appointment.components.DoctorInformation
import com.up.clinica_digital.presentation.appointment.components.TopNavigationBar
import com.up.clinica_digital.ui.theme.ClinicaDigitalTheme

@Composable
fun AppointmentScheduleScreen(
    viewModel: AppointmentViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by viewModel.doctorState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadDoctor("1")
    }

    Scaffold(
        topBar = {
            TopNavigationBar(navController)
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top

        ) {
            Column(Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Agendamento",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold

                )
                when (state) {
                    is DoctorUiState.Loading -> Text("Carregando médico...")
                    is DoctorUiState.Error -> Text("Erro: ${(state as DoctorUiState.Error).message}")
                    is DoctorUiState.Success -> DoctorInformation((state as DoctorUiState.Success).doctor)
                    DoctorUiState.Idle -> Text("Nenhum médico carregado")
                }
                CalendarTimeDatePicker {  }

            }
        }
    }
}

@Preview
@Composable
fun AppointmentScheduleScreenPreview() {
    ClinicaDigitalTheme {
        val navController = rememberNavController()
        AppointmentScheduleScreen(
            navController = navController
        )
    }
}