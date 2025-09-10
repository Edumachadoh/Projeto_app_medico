package com.up.clinica_digital.domain.usecase.appointment

import com.up.clinica_digital.domain.repository.IAppointmentRepository
import com.up.clinica_digital.domain.model.Appointment

class ListByPatientUseCase(
    private val repository: IAppointmentRepository
) {
    suspend fun invoke(patientId: String): List<Appointment> =
        repository.listByPatient(patientId)
}