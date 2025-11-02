package com.up.clinica_digital.presentation.appointment.doctor

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Patient
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * PEDRO:
 * Item to show in the LazyColumn
 * of [AppointmentsAgendaScreen].
 *
 * @param appointment Appointment object.
 * @param patient Patient object.
 * @param onAppointmentClick Function executed when clicking the "View Details" button.
 */

@Composable
fun AgendaItem(
    appointment: Appointment,
    patient: Patient?,
    onAppointmentClick: (String) -> Unit
) {
    //PEDRO: it's a card that shows the patient's name and cpf
    //in addition to the date and time of the appointment following formatting
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
                text = "CPF: ${patient?.cpf ?: "Não informado"}",
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

//PEDRO: date formatting
private fun formatDate(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
    return date.format(formatter)
}

//PEDRO: time formatting
private fun formatTime(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    return date.format(formatter)
}