package com.up.clinica_digital.data.remote.dto

// ANA: Complete response structure from CFM API for CRM validation
data class CfmApiResponse(
    val url: String?,
    val total: Int?,
    val status: String?,
    val mensagem: String?,
    val api_limite: String?,
    val api_consultas: String?,
    val item: List<CfmApiDoctor>?
)