package com.up.clinica_digital.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.AppointmentStatus
import com.up.clinica_digital.domain.repository.AppointmentRepository
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime

class FirebaseAppointmentRepositoryImpl(
    firestore: FirebaseFirestore
) : FirebaseCrudRepositoryImpl<Appointment>("appointments", firestore), AppointmentRepository {

    override fun Appointment.toMap(): Map<String, Any> = mapOf(
        "id" to id,
        "doctorId" to doctorId,
        "patientId" to patientId,
        "scheduledAt" to scheduledAt.toString(),
        "status" to status.name
    )

    override fun DocumentSnapshot.toDomain(): Appointment? {
        val data = data ?: return null

        val statusStr = data["status"] as? String ?: AppointmentStatus.SCHEDULED.name
        val status = try {
            AppointmentStatus.valueOf(statusStr)
        } catch (e: Exception) {
            AppointmentStatus.SCHEDULED
        }

        return Appointment(
            id = id,
            doctorId = data["doctorId"] as? String ?: "",
            patientId = data["patientId"] as? String ?: "",
            scheduledAt = LocalDateTime.parse(data["scheduledAt"] as? String ?: LocalDateTime.now().toString()),
            status = status
        )
    }

    override suspend fun listByDoctor(doctorId: String): List<Appointment> {
        val snapshot = collection
            .whereEqualTo("doctorId", doctorId)
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toDomain() }
    }

    override suspend fun listByPatient(patientId: String): List<Appointment> {
        val snapshot = collection
            .whereEqualTo("patientId", patientId)
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toDomain() }
    }
}