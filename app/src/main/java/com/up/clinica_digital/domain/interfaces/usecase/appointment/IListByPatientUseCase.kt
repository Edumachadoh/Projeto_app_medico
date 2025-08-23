package com.up.clinica_digital.domain.interfaces.usecase.appointment

import com.up.clinica_digital.domain.model.Appointment

interface IListByPatientUseCase {
    suspend operator fun invoke(patientId: String): List<Appointment>
}