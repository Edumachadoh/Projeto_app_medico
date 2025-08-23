package com.up.clinica_digital.data.remote.datasource

import com.up.clinica_digital.data.remote.api.CfmApiService
import com.up.clinica_digital.data.remote.dto.CfmApiDoctor
import javax.inject.Inject

class CfmRemoteDataSource @Inject constructor(
    private val api: CfmApiService
) {
    suspend fun validateCRM(crm: String, uf: String, apiKey: String): List<CfmApiDoctor> {
        val response = api.validateCRM(uf = uf, query = crm, apiKey = apiKey)
        return response.item ?: emptyList()
    }
}
