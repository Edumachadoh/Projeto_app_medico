package com.up.clinica_digital.data.remote.api

import com.up.clinica_digital.data.remote.dto.CfmApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CfmApiService {
    @GET("api/index.php")
    suspend fun validateCRM(
        @Query("tipo") tipo: String = "crm",
        @Query("uf") uf: String,
        @Query("q") query: String,
        @Query("chave") apiKey: String,
        @Query("destino") destino: String = "json"
    ): CfmApiResponse
}
