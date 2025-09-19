package com.up.clinica_digital.domain.model

data class LoginResult(
    val userId: String,
    val role: UserRole
)