package com.up.clinica_digital.presentation.appointment.doctor

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.Patient
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@Composable
fun AgendaItem(
    appointment: Appointment,
    patient: Patient?,
    onAppointmentClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = patient?.name ?: "Paciente não encontrado",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "CPF: ${patient?.cpf ?: "Não informada"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Data: ${formatDate(appointment.scheduledAt)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Hora: ${formatTime(appointment.scheduledAt)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onAppointmentClick(appointment.id) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Ver Detalhes")
            }
        }
    }
}

private fun formatDate(date: LocalDateTime): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()))
}

private fun formatTime(date: LocalDateTime): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()))
}