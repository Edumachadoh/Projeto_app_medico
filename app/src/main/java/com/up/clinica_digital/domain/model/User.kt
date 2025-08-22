package com.up.clinica_digital.domain.model

import android.os.Parcelable
import com.up.clinica_digital.domain.common.HasId
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class User(
    open override val id: String,
    open val name: String,
    open val email: String,
    open val cpf: String,
    open val passwordHash: String
) : HasId, Parcelable