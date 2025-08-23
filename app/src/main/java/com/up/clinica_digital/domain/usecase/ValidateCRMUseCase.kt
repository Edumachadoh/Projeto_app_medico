package com.up.clinica_digital.domain.usecase

import com.up.clinica_digital.domain.interfaces.repository.IDoctorRepository
import com.up.clinica_digital.domain.interfaces.usecase.user.IValidateCRMUseCase

class ValidateCRMUseCase(
    private val repository: IDoctorRepository
) : IValidateCRMUseCase {
    override suspend fun invoke(crm: String): Boolean =
        repository.validateCRM(crm)
}