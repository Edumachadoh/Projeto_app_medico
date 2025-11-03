package com.up.clinica_digital.domain.usecase.user

import com.up.clinica_digital.domain.repository.UserAuthRepository

//CAUE: The "user" folder inside "usecase" groups all business logic
//related to user authentication and management.
//Just like the "appointment" folder handles appointment-specific logic,
//this one focuses on operations involving users.

//The "GetCurrentUserIdUseCase" class provides a simple way to access
//the currently authenticated user's ID.

class GetCurrentUserIdUseCase(
    private val repository: UserAuthRepository
) {
    fun invoke(): String? =
        repository.currentUserId()
}