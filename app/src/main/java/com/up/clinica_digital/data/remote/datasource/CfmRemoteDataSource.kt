package com.up.clinica_digital.data.remote.datasource

import com.up.clinica_digital.data.remote.api.CfmApiService
import com.up.clinica_digital.data.remote.dto.CfmApiDoctor
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class CfmRemoteDataSource @Inject constructor(
    private val api: CfmApiService
) {
    suspend fun validateCRM(crm: String, uf: String, apiKey: String): List<CfmApiDoctor> {
        return try {
            withTimeout(5000L) {
                val response = api.validateCRM(uf = uf, query = crm, apiKey = apiKey)
                response.item ?: emptyList()
            }
        } catch (e: Exception) {
            println("Timeout or CFM API error: ${e.message}")
            emptyList()
        }
    }
}
