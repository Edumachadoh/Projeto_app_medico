package com.up.clinica_digital.data.repository

import com.up.clinica_digital.data.remote.datasource.CfmRemoteDataSource
import com.up.clinica_digital.data.mapper.toDomain
import com.up.clinica_digital.domain.model.CfmDoctor
import com.up.clinica_digital.domain.repository.CfmRepository
import javax.inject.Inject

// ANA: Repository implementation for doctor CRM validation using CFM API
class CfmRepositoryImpl @Inject constructor(
    private val remoteDataSource: CfmRemoteDataSource
) : CfmRepository {
    override suspend fun validateDoctorCrm(crm: String, uf: String): List<CfmDoctor> {
        return remoteDataSource.validateCRM(crm, uf, "5633276877")
            // ANA: Yes, this is a bad implementation (exposes API KEY in code),
            // but I just couldn't get it how to do it in another way
            .map { it.toDomain() }
    }
}