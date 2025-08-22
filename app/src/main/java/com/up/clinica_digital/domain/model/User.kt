package com.up.clinica_digital.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class User(
    open val id: String,
    open val name: String,
    open val email: String,
    open val cpf: String,
    open val passwordHash: String
) : Parcelable