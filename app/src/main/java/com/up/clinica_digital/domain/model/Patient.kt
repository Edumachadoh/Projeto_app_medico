package com.up.clinica_digital.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

// Automatically generates the code needed to pass this object between screens
@Parcelize

//Data model for a patient, extending the base 'User' class
data class Patient(
    override val id: String, //Unique identifier of the patient
    override val name: String, //User's full name
    override val email: String, //User's login e-mail
    override val cpf: String, //User's CPF document number
    override val passwordHash: String, //Hashed password for authentication

    //Patient-specific property
    val birthDate: LocalDate //Patient's date of birth
) : User(id, name, email, cpf, passwordHash, role = UserRole.PATIENT), Parcelable
// Extends User, passing base fields and setting the role
// Parcelable implements contract for passing data between Android components