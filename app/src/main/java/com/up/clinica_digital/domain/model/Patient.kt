package com.up.clinica_digital.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Patient(
    override val id: String,
    override val name: String,
    override val email: String,
    override val cpf: String,
    override val passwordHash: String,
    val birthDate: LocalDate
) : User(id, name, email, cpf, passwordHash, role = UserRole.PATIENT), Parcelable