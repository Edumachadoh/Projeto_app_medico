package com.up.clinica_digital.domain.usecase.user

import com.up.clinica_digital.domain.model.Patient
import com.up.clinica_digital.domain.repository.UserAuthRepository

class RegisterPatientUseCase(
    private val repository: UserAuthRepository
) {
    suspend fun invoke(patient: Patient): String? =
        repository.registerPatient(patient)
}