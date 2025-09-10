package com.up.clinica_digital.domain.usecase.user

import com.up.clinica_digital.domain.repository.IUserAuthRepository
import com.up.clinica_digital.domain.interfaces.usecase.user.ILoginUserUseCase

class LoginUserUseCase(
    private val repository: IUserAuthRepository
) : ILoginUserUseCase {
    override suspend fun invoke(email: String, password: String): String? =
        repository.login(email, password)
}