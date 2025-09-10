package com.up.clinica_digital.domain.usecase.appointment

import com.up.clinica_digital.domain.repository.IAppointmentRepository
import com.up.clinica_digital.domain.model.Appointment

class ListByDoctorUseCase(
    private val repository: IAppointmentRepository
) {
    suspend fun invoke(doctorId: String): List<Appointment> =
        repository.listByDoctor(doctorId)
}