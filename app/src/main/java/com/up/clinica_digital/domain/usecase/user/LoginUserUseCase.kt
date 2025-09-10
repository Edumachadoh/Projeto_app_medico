package com.up.clinica_digital.domain.usecase.user

import com.up.clinica_digital.domain.repository.UserAuthRepository

class LoginUserUseCase(
    private val repository: UserAuthRepository
) {
    suspend fun invoke(email: String, password: String): String? =
        repository.login(email, password)
}