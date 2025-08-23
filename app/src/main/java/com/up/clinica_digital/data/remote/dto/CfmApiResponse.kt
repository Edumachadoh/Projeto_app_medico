package com.up.clinica_digital.data.remote.dto

data class CfmApiResponse(
    val url: String?,
    val total: Int?,
    val status: String?,
    val mensagem: String?,
    val api_limite: String?,
    val api_consultas: String?,
    val item: List<CfmApiDoctor>?
)