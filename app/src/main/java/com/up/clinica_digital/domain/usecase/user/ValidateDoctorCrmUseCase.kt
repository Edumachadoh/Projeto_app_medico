package com.up.clinica_digital.domain.usecase.user

import com.up.clinica_digital.domain.model.CfmDoctor
import com.up.clinica_digital.domain.repository.CfmRepository

class ValidateDoctorCrmUseCase(
    private val repository: CfmRepository
) {
    suspend operator fun invoke(crm: String, uf: String): List<CfmDoctor> {
        return repository.validateDoctorCrm(crm, uf)
    }
}