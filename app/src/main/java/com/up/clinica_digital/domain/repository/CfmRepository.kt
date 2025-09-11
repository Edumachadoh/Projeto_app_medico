package com.up.clinica_digital.domain.repository

import com.up.clinica_digital.domain.model.CfmDoctor

interface CfmRepository {
    suspend fun validateDoctorCrm(crm: String, uf: String): List<CfmDoctor>
}
