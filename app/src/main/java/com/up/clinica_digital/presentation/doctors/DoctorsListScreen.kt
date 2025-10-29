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
 * Tela que mostra a lista de todos os médicos
 * cadastrado para o paciente logado
 * quando o paciente clicar em um médico
 * irá redireciona-lo a tela detalhes médico
 * onde o paciente poderá começar o processo
 * de agendamento de consulta
 *
 * @param viewModel viewmodel que carrega a lista de médicos
 * @param navController controlador de navegação que permite
 * o paciente navegar na tela dos detalhes do doutor selecionado
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

