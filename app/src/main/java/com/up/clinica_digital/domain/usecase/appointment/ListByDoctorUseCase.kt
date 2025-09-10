package com.up.clinica_digital.domain.usecase.appointment

import com.up.clinica_digital.domain.repository.IAppointmentRepository
import com.up.clinica_digital.domain.interfaces.usecase.appointment.IListByDoctorUseCase
import com.up.clinica_digital.domain.model.Appointment

class ListByDoctorUseCase(
    private val repository: IAppointmentRepository
) : IListByDoctorUseCase {
    override suspend fun invoke(doctorId: String): List<Appointment> =
        repository.listByDoctor(doctorId)
}