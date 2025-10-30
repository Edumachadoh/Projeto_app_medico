package com.up.clinica_digital.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Automatically generates the code needed to pass this object between screens
@Parcelize

// Data model for a doctor, extending the base 'User' class
data class Doctor(
    // Properties from the base 'User' class
    override val id: String, //Unique identifier for the user
    override val name: String, //User's full name
    override val email: String, //User's login e-mail
    override val cpf: String, //User's CPF document number
    override val passwordHash: String, //Hashed password for authentication

    // Doctor-specific properties
    val crm: String, //Medical registration number (CRM)
    val rqe: String, //Specialist qualification register (RQE)
    val specialization: String, //Medical specialization
    val uf: String, //State of registration
) : User(id, name, email, cpf, passwordHash, role = UserRole.DOCTOR), Parcelable
// Extends User, passing base fields and setting the role
// Parcelable implements contract for passing data between Android components