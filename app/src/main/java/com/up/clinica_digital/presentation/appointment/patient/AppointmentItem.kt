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
 * Item para exibir na LazyColumn da tela [ScheduledAppointmentsScreen].
 *
 * Este Composable mostra um card com as informações resumidas de um agendamento,
 * incluindo o nome do médico, especialidade, data e hora.
 *
 * @param appointment O objeto [Appointment] (consulta) a ser exibido.
 * @param doctor O objeto [Doctor] (médico) associado à consulta.
 * @param onAppointmentClick Função lambda executada quando o botão "Ver Detalhes" é clicado.
 */
@Composable
fun AppointmentItem(
    appointment: Appointment,
    doctor: Doctor?,
    onAppointmentClick: (String) -> Unit
) {
    //é um cartão que mostra nome do doutor
    //além de data e horário da consulta seguindo uma formatação
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
 * Formata um [LocalDateTime] para uma string de data (dd/MM/yyyy).
 */
private fun formatDate(date: LocalDateTime): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()))
}

/**
 * Formata um [LocalDateTime] para uma string de hora (HH:mm).
 */
private fun formatTime(date: LocalDateTime): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()))
}