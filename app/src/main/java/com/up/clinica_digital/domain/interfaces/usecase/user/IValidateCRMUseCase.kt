package com.up.clinica_digital.domain.interfaces.usecase.user

interface IValidateCRMUseCase {
    suspend operator fun invoke(crm: String, uf: String): Boolean
}