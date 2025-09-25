package com.up.clinica_digital.presentation.appointment.agenda

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.Doctor

@Composable
fun AppointmentItem(appointment: Appointment, doctor: Doctor) {
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