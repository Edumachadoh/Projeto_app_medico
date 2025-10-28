package com.up.clinica_digital.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.model.Patient
import com.up.clinica_digital.domain.repository.PatientRepository
import java.time.LocalDate

// ANA: Firebase implementation for patient data management
class FirebasePatientRepositoryImpl(
    firestore: FirebaseFirestore
) : FirebaseCrudRepositoryImpl<Patient>("patients", firestore), PatientRepository {

    // ANA: Converts patient object to Firestore data format with birth date
    override fun Patient.toMap(): Map<String, Any> = mapOf(
        "id" to id,
        "name" to name,
        "email" to email,
        "cpf" to cpf,
        "passwordHash" to passwordHash,
        "birthDate" to birthDate.toString()
    )

    // ANA: Converts Firestore document back to patient object
    override fun DocumentSnapshot.toDomain(): Patient? {
        val data = data ?: return null
        return Patient(
            id = id,
            name = data["name"] as? String ?: "",
            email = data["email"] as? String ?: "",
            cpf = data["cpf"] as? String ?: "",
            passwordHash = data["passwordHash"] as? String ?: "",
            birthDate = LocalDate.parse(data["birthDate"] as? String ?: LocalDate.now().toString())
        )
    }
}