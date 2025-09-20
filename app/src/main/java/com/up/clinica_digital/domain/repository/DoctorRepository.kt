package com.up.clinica_digital.domain.repository

import com.up.clinica_digital.domain.model.Doctor

interface DoctorRepository : CrudRepository<Doctor> {
    suspend fun listByUFAndSpeciality(uf: String, speciality: String): List<Doctor>
}