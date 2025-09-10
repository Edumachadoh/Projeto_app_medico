package com.up.clinica_digital.domain.usecase.user

import com.up.clinica_digital.domain.repository.IUserAuthRepository

class LoginUserUseCase(
    private val repository: IUserAuthRepository
) {
    suspend fun invoke(email: String, password: String): String? =
        repository.login(email, password)
}