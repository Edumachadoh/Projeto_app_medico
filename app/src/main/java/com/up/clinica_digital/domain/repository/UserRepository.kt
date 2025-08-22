package com.up.clinica_digital.domain.repository

import com.up.clinica_digital.domain.model.User

interface UserRepository<T> : CrudRepository<User> {
    suspend fun login(email: String, password: String): User?
}