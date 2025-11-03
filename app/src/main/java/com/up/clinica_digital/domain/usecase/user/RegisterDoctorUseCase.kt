package com.up.clinica_digital.domain.usecase.user

import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.repository.UserAuthRepository

//CAUE: The "RegisterDoctorUseCase" defines the business rule for registering a new doctor in the system.
//It depends on the "UserAuthRepository", which handles the process of creating
//and storing the doctor's account

class RegisterDoctorUseCase(
    private val repository: UserAuthRepository
) {
    suspend fun invoke(doctor: Doctor): String? =
        repository.registerDoctor(doctor)
}