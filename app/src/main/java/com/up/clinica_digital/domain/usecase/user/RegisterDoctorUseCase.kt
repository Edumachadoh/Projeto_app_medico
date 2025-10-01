package com.up.clinica_digital.domain.usecase.user

import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.repository.UserAuthRepository

class RegisterDoctorUseCase(
    private val repository: UserAuthRepository
) {
    suspend fun invoke(doctor: Doctor): String? =
        repository.registerDoctor(doctor)
}