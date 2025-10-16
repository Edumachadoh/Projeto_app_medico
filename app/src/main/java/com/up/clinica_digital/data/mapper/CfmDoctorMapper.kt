package com.up.clinica_digital.data.mapper

import com.up.clinica_digital.data.remote.dto.CfmApiDoctor
import com.up.clinica_digital.domain.model.CfmDoctor

// ANA: Converts API doctor data to app's domain model for clean architecture
fun CfmApiDoctor.toDomain() = CfmDoctor(
    name = nome ?: "",
    crm = numero ?: "",
    uf = uf ?: "",
    status = situacao ?: ""
)