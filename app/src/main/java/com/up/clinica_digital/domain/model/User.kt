package com.up.clinica_digital.domain.model

import android.os.Parcelable
import com.up.clinica_digital.domain.common.HasId
import kotlinx.parcelize.Parcelize

// Automatically generates the code needed to pass this object between screens
@Parcelize

//Base data model for a User. 'open' allows this class to be inherited (e.g., by Patient, Doctor)
//Properties are 'open' so subclasses can override them
open class User(
    open override val id: String, //Unique identifier (from HasId)
    open val name: String, //User's full name
    open val email: String, //User's login e-mail
    open val cpf: String, //User's CPF document number
    open val passwordHash: String, //Hashed password for authentication
    open val role: UserRole //Defines user type (e.g., PATIENT, DOCTOR)
) : HasId, Parcelable //Implements: ID contract (HasId) and data passing (Parcelable)