package com.up.clinica_digital.domain.interfaces.usecase.user

interface ILoginUserUseCase {
    suspend operator fun invoke(email: String, password: String): String?
}