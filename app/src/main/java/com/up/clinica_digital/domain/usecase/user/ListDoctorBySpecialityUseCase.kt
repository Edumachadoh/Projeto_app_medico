package com.up.clinica_digital.domain.usecase.user

import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.repository.DoctorRepository

class ListDoctorBySpecialityUseCase (
    private val repository: DoctorRepository
) {
    suspend fun invoke(specialization: String): List<Doctor> =
        repository.listBySpeciality(specialization)
}