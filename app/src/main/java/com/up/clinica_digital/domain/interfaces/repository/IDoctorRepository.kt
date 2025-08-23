package com.up.clinica_digital.domain.interfaces.repository

import com.up.clinica_digital.domain.model.Doctor

interface IDoctorRepository : ICrudRepository<Doctor> {
    suspend fun validateCRM(crm: String): Boolean
}