package com.up.clinica_digital.data.mapper

import com.up.clinica_digital.data.remote.dto.CfmApiDoctor
import com.up.clinica_digital.domain.model.CfmDoctor

fun CfmApiDoctor.toDomain() = CfmDoctor(
    name = nome ?: "",
    crm = numero ?: "",
    uf = uf ?: "",
    status = situacao ?: ""
)