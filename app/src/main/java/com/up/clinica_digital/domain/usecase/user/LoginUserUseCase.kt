package com.up.clinica_digital.domain.usecase.user

import com.up.clinica_digital.domain.model.LoginResult
import com.up.clinica_digital.domain.repository.UserAuthRepository

//CAUE: The "LoginUserUseCase" class defines the business rule for logging a user into the system.
//It receives an instance of "UserAuthRepository", which handles the actual
//authentication process

class LoginUserUseCase(
    private val repository: UserAuthRepository
) {
    suspend fun invoke(email: String, password: String): LoginResult? =
        repository.login(email, password)
}