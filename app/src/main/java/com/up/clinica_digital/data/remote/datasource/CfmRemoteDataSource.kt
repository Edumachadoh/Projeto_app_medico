package com.up.clinica_digital.data.remote.datasource

import com.up.clinica_digital.data.remote.api.CfmApiService
import com.up.clinica_digital.data.remote.dto.CfmApiDoctor
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class CfmRemoteDataSource @Inject constructor(
    private val api: CfmApiService
) {
    suspend fun validateCRM(crm: String, uf: String, apiKey: String): List<CfmApiDoctor> {
        return try {
            val response = withTimeout(5000L) {
                api.validateCRM(uf = uf, query = crm, apiKey = apiKey)
            }

            if (response.status == "true" && !response.item.isNullOrEmpty()) {
                response.item
            } else {
                println("Empty or invalid API response: total=${response.total}")
                emptyList()
            }

        } catch (e: TimeoutCancellationException) {
            println("Timeout while calling CFM API: ${e.message}")
            emptyList()
        } catch (e: Exception) {
            println("CFM API error: ${e.message}")
            emptyList()
        }
    }
}
