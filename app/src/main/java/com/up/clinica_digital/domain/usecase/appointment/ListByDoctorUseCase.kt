package com.up.clinica_digital.domain.usecase.appointment

import com.up.clinica_digital.domain.repository.AppointmentRepository
import com.up.clinica_digital.domain.model.Appointment

class ListByDoctorUseCase(
    private val repository: AppointmentRepository
) {
    suspend fun invoke(doctorId: String): List<Appointment> =
        repository.listByDoctor(doctorId)
}