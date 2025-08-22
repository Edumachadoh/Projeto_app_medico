package com.up.clinica_digital.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Doctor(
    override val id: String,
    override val name: String,
    override val email: String,
    override val cpf: String,
    override val passwordHash: String,
    val crm: String,
    val rqe: String,
    val specialization: String
) : User(id, name, email, cpf, passwordHash), Parcelable