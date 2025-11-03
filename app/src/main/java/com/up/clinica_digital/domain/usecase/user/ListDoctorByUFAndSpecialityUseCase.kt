package com.up.clinica_digital.domain.usecase.user

import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.repository.DoctorRepository

//CAUE: The "ListDoctorByUFAndSpecialityUseCase" defines the business rule
//for retrieving all doctors filtered by their state (UF) and medical speciality.
//It uses the "DoctorRepository" to access the data source that stores doctor information.

class ListDoctorByUFAndSpecialityUseCase(
    private val repository: DoctorRepository
) {
    suspend fun invoke(uf: String, speciality: String): List<Doctor> =
        repository.listByUFAndSpeciality(uf, speciality)
}