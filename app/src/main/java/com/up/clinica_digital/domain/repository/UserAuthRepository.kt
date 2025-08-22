package com.up.clinica_digital.domain.repository

interface UserAuthRepository {
    suspend fun login(email: String, password: String): String?
    suspend fun register(email: String, password: String): String?
    suspend fun logout()
    fun currentUserId(): String?
}