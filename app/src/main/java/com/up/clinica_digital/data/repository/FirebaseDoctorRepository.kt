package com.up.clinica_digital.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.repository.DoctorRepository

class FirebaseDoctorRepository(
    firestore: FirebaseFirestore
) : FirebaseCrudRepository<Doctor>("doctors", firestore), DoctorRepository {

    override fun Doctor.toMap(): Map<String, Any> = mapOf(
        "id" to id,
        "name" to name,
        "email" to email,
        "cpf" to cpf,
        "passwordHash" to passwordHash,
        "crm" to crm,
        "rqe" to rqe,
        "specialization" to specialization,
        "uf" to uf
    )

    override fun DocumentSnapshot.toDomain(): Doctor? {
        val data = data ?: return null
        return Doctor(
            id = id,
            name = data["name"] as? String ?: "",
            email = data["email"] as? String ?: "",
            cpf = data["cpf"] as? String ?: "",
            passwordHash = data["passwordHash"] as? String ?: "",
            crm = data["crm"] as? String ?: "",
            rqe = data["rqe"] as? String ?: "",
            specialization = data["specialization"] as? String ?: "",
            uf = data["uf"] as? String ?: ""
        )
    }
}