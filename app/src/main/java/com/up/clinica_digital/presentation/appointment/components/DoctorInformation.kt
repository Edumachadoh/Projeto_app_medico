package com.up.clinica_digital.presentation.appointment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.ui.theme.ClinicaDigitalTheme

@Composable
fun DoctorInformation(
    doctorInfo: Doctor
) {
        Column {
            Text(
                text = doctorInfo.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "CRM: ${doctorInfo.crm} | RQE: ${doctorInfo.rqe}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = doctorInfo.specialization,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
}

@Preview(showBackground = true)
@Composable
fun DoctorInformationPreview() {
    ClinicaDigitalTheme {
        DoctorInformation(
            Doctor(
                name = "Pedro",
                id = "123",
                email = "pedro@gmail.com",
                cpf = "12345677890",
                passwordHash = "423423423432",
                crm = "341312",
                rqe = "3214312",
                specialization = "Fisioterapeuta",
                uf = "PR",
            )
        )
    }
}
