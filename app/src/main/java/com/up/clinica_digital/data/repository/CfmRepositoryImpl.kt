package com.up.clinica_digital.data.repository

import com.up.clinica_digital.data.remote.datasource.CfmRemoteDataSource
import com.up.clinica_digital.data.mapper.toDomain
import com.up.clinica_digital.domain.model.CfmDoctor
import com.up.clinica_digital.domain.repository.CfmRepository
import javax.inject.Inject

class CfmRepositoryImpl @Inject constructor(
    private val remoteDataSource: CfmRemoteDataSource
) : CfmRepository {
    override suspend fun validateDoctorCrm(crm: String, uf: String): List<CfmDoctor> {
        return remoteDataSource.validateCRM(crm, uf, "5633276877")
            .map { it.toDomain() }
    }
}
