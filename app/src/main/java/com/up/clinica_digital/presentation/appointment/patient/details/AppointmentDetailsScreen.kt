package com.up.clinica_digital.presentation.appointment.patient.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.AppointmentStatus
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.presentation.component.top_nav.TopNavigationBar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Tela que exibe os detalhes de um agendamento para o paciente logado.
 *
 * Esta tela gerencia o estado da UI (Loading, Error, Success) e exibe
 * o conteúdo principal ou indicadores de feedback apropriados.
 * A consulta é selecionada na tela [ScheduledAppointmentsScreen]
 *
 * @param navController O controlador de navegação para ações.
 * @param viewModel O ViewModel que gerencia o estado desta tela.

 */

@Composable
fun AppointmentDetailsScreen(
    navController: NavHostController,
    viewModel: AppointmentDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    //essa tela mostra:
    //Nome do médico
    //cpf do médico
    //data e hora da consulta
    //botão para cancelar a consulta
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
                    Text(text = "Erro: ${state.message}", color = MaterialTheme.colorScheme.error)
                }
                is AppointmentDetailsUiState.Success -> {
                    AppointmentDetailsContent(
                        appointment = state.appointment,
                        doctor = state.doctor,
                        onCancel = {
                            viewModel.cancelAppointment {
                                navController.popBackStack()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun AppointmentDetailsContent(
    appointment: Appointment,
    doctor: Doctor,
    onCancel: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 4.dp,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = doctor.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                InfoRow(
                    icon = Icons.Default.AccountCircle,
                    text = "Especialidade: ${doctor.specialization}"
                )
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow(
                    icon = Icons.Default.DateRange,
                    text = "Data: ${formatDate(appointment.scheduledAt)}\nHora: ${
                        formatTime(
                            appointment.scheduledAt
                        )
                    }"
                )
                Spacer(modifier = Modifier.height(16.dp))
                StatusBadge(status = appointment.status)
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Empurra o botão para baixo

        Button(
            enabled = appointment.status == AppointmentStatus.SCHEDULED || appointment.status == AppointmentStatus.CONFIRMED,
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Cancelar Consulta")
        }
    }
}

@Composable
private fun InfoRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun StatusBadge(status: AppointmentStatus) {
    val backgroundColor = when (status) {
        AppointmentStatus.CANCELED -> MaterialTheme.colorScheme.errorContainer
        else -> Color(0xFFD1FAE5) // Verde claro
    }
    val contentColor = when (status) {
        AppointmentStatus.CANCELED -> MaterialTheme.colorScheme.onErrorContainer
        else -> Color(0xFF065F46) // Verde escuro
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        Surface(
            color = backgroundColor,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = status.toString().replaceFirstChar { it.titlecase(Locale.getDefault()) },
                color = contentColor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}


private fun formatDate(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
    return date.format(formatter)
}

private fun formatTime(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    return date.format(formatter)
}