package com.up.clinica_digital.domain.usecase.user

import com.up.clinica_digital.domain.repository.UserAuthRepository

class GetCurrentUserIdUseCase(
    private val repository: UserAuthRepository
) {
    suspend fun invoke(): String? =
        repository.currentUserId()
}