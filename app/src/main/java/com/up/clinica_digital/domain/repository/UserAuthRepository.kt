package com.up.clinica_digital.domain.repository

import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.LoginResult
import com.up.clinica_digital.domain.model.Patient

interface UserAuthRepository {
    suspend fun login(email: String, password: String): LoginResult?
    suspend fun registerDoctor(doctor: Doctor): String?
    suspend fun registerPatient(patient: Patient): String?
    suspend fun logout()
    fun currentUserId(): String?
}