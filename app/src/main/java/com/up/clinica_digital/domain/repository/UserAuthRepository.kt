package com.up.clinica_digital.domain.repository

import com.up.clinica_digital.domain.model.LoginResult

interface UserAuthRepository {
    suspend fun login(email: String, password: String): LoginResult?
    suspend fun register(email: String, password: String): String?
    suspend fun logout()
    fun currentUserId(): String?
}