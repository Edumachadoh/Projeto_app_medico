package com.up.clinica_digital.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.AppointmentStatus
import com.up.clinica_digital.domain.repository.AppointmentRepository
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime

// ANA: Firebase implementation for appointment data management with status tracking
class FirebaseAppointmentRepositoryImpl(
    firestore: FirebaseFirestore
) : FirebaseCrudRepositoryImpl<Appointment>("appointments", firestore), AppointmentRepository {

    // ANA: Converts appointment object to Firestore data format for storage
    override fun Appointment.toMap(): Map<String, Any> = mapOf(
        "id" to id,
        "doctorId" to doctorId,
        "patientId" to patientId,
        "scheduledAt" to scheduledAt.toString(),
        "status" to status.name
    )

    // ANA: Converts Firestore document back to appointment object with status parsing
    override fun DocumentSnapshot.toDomain(): Appointment? {
        val data = data ?: return null

        val statusStr = data["status"] as? String ?: AppointmentStatus.SCHEDULED.name
        // ANA: Parses status string to AppointmentStatus enum value
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

    // ANA: Fetches all appointments for a specific doctor from Firestore
    override suspend fun listByDoctor(doctorId: String): List<Appointment> {
        val snapshot = collection
            .whereEqualTo("doctorId", doctorId)
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toDomain() }
    }

    // ANA: Fetches all appointments for a specific patient from Firestore
    override suspend fun listByPatient(patientId: String): List<Appointment> {
        val snapshot = collection
            .whereEqualTo("patientId", patientId)
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toDomain() }
    }
}