package com.up.clinica_digital.data.remote.dto

data class CfmApiDoctor(
    val id: String?,
    val uid: String?,
    val tipo: String?,
    val nome: String?,
    val numero: String?,
    val profissao: String?,
    val uf: String?,
    val situacao: String?,
    val link: String?
)