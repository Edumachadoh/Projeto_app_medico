package com.up.clinica_digital.presentation.component.top_nav

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.ui.theme.ClinicaDigitalTheme

/**
 * PEDRO:
 * Composable component that shows the doctor information for the patient
 * in any screen that it's called.
 *
 * @param doctor The doctor object that determines the information shown.
 */
@Composable
fun DoctorInformation(
    doctor: Doctor
) {
        Column {
            Text(
                text = doctor.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "CRM: ${doctor.crm} | RQE: ${doctor.rqe}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = doctor.specialization,
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
