package com.up.clinica_digital.presentation.doctors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.up.clinica_digital.presentation.component.top_nav.TopNavigationBar
import com.up.clinica_digital.presentation.doctor.DoctorsViewModel

@Composable
fun DoctorDetails(navController: NavController, viewModel: DoctorsViewModel){
    Scaffold(topBar = { TopNavigationBar(navController) })
    { innerPadding ->
        val selectedDoctor = viewModel.uiState.collectAsState().value.selectedDoctor
        Column(
            modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column {
                Text(text = selectedDoctor.name)
                Text(text = selectedDoctor.specialization)
                Text(text = "CRM:${selectedDoctor.crm}")
                Text(text = "RQE:${selectedDoctor.rqe}")
            }
        }
    }
}
