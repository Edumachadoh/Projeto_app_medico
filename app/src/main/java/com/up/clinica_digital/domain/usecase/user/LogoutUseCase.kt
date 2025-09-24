package com.up.clinica_digital.domain.usecase.user

import com.up.clinica_digital.domain.repository.UserAuthRepository

class LogoutUseCase(
    private val repository: UserAuthRepository
) {
    suspend fun invoke() =
        repository.logout()
}