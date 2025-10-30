package com.up.clinica_digital.domain.model

//Data model representing the essential info from a successful login
data class LoginResult(
    val userId: String, //The unique ID of the authenticated user
    val role: UserRole //The user's role (DOCTOR, PATIENT)
)