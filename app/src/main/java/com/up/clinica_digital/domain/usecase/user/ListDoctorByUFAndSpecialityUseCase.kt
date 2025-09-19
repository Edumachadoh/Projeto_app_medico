package com.up.clinica_digital.domain.usecase.user

import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.repository.DoctorRepository

class ListDoctorByUFAndSpecialityUseCase(
    private val repository: DoctorRepository
) {
    suspend fun invoke(uf: String, speciality: String): List<Doctor> =
        repository.listByUFAndSpeciality(uf, speciality)
}