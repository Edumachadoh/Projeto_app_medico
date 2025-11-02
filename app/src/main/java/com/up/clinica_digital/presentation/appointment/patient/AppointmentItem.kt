package com.up.clinica_digital.presentation.appointment.patient

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Doctor
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale

/**
 * PEDRO:
 * Item to display in the LazyColumn of the [ScheduledAppointmentsScreen].
 *
 * This Composable shows a card with the summary information of an appointment,
 * including the doctor's name, specialty, date, and time.
 *
 * @param appointment The [Appointment] object (appointment) to be displayed.
 * @param doctor The [Doctor] object (doctor) associated with the appointment.
 * @param onAppointmentClick Lambda function executed when the "View Details" button is clicked.
 */
@Composable
fun AppointmentItem(
    appointment: Appointment,
    doctor: Doctor?,
    onAppointmentClick: (String) -> Unit
) {

    //PEDRO:
    //it's a card that shows the doctor's name
    //in addition to the date and time of the appointment following formatting
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = doctor?.name ?: "Médico não encontrado",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Especialidade: ${doctor?.specialization ?: "Não informada"}",
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

/**
 * PEDRO:
 * Formats a [LocalDateTime] into a date string (dd/MM/yyyy).
 */
private fun formatDate(date: LocalDateTime): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()))
}

/**
 * PEDRO:
 * Formats a [LocalDateTime] into a time string (HH:mm).
 */
private fun formatTime(date: LocalDateTime): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()))
}