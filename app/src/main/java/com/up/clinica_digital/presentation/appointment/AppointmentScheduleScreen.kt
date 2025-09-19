package com.up.clinica_digital.presentation.appointment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.up.clinica_digital.presentation.component.top_nav.TopNavigationBar
import com.up.clinica_digital.ui.theme.ClinicaDigitalTheme

@Composable
fun AppointmentScheduleScreen(navController: NavHostController) {
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
            Column(Modifier.padding(24.dp)) {
                Text(
                    text = "Agendamento",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold

                )
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