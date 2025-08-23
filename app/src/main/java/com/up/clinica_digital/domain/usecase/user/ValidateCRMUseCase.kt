package com.up.clinica_digital.domain.usecase.user

import com.up.clinica_digital.data.remote.datasource.CfmRemoteDataSource
import com.up.clinica_digital.domain.interfaces.usecase.user.IValidateCRMUseCase
import javax.inject.Inject

class ValidateCRMUseCase @Inject constructor(
    private val cfmRemoteDataSource: CfmRemoteDataSource
) : IValidateCRMUseCase {
    override suspend operator fun invoke(crm: String, uf: String): Boolean {
        val apiKey = "5633276877" // TODO: Injetar via BuildConfig
        val doctors = cfmRemoteDataSource.validateCRM(crm, uf, apiKey)
        return doctors.any { it.numero == crm && it.situacao == "Ativo" }
    }
}