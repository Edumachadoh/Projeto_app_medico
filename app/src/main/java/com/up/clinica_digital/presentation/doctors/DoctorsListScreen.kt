package com.up.clinica_digital.presentation.doctors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.presentation.navigation.Screen

/**
 * PEDRO:
 * Screen that shows the list of all registered
 * doctors for the logged-in patient.
 * When the patient clicks on a doctor,
 * it will redirect them to the doctor details screen
 * where the patient can begin the process
 * of scheduling an appointment.
 *
 * @param viewModel ViewModel that loads the list of doctors.
 * @param navController Navigation controller that allows
 * the patient to navigate to the selected doctor's details screen.
 */
@Composable
fun DoctorsListScreen(
    viewModel: DoctorsViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Especialidade") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))
            Text("Especialidade", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                uiState.error?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.doctors) { doctor ->
                        OutlinedButton(onClick = {navController.navigate(Screen.DoctorDetails.createRoute(doctorId = doctor.id))}) {
                            DoctorItem(doctor = doctor)
                        }
                    }
                }
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