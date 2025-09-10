package com.up.clinica_digital.domain.usecase.appointment

import com.up.clinica_digital.domain.repository.AppointmentRepository
import com.up.clinica_digital.domain.model.Appointment

class ListByPatientUseCase(
    private val repository: AppointmentRepository
) {
    suspend fun invoke(patientId: String): List<Appointment> =
        repository.listByPatient(patientId)
}