package com.up.clinica_digital.domain.interfaces.usecase.appointment

import com.up.clinica_digital.domain.model.Appointment

interface IListByDoctorUseCase {
    suspend operator fun invoke(doctorId: String): List<Appointment>
}