package com.up.clinica_digital.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.AppointmentStatus
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.time.LocalDateTime

class FirebaseAppointmentRepositoryTest {

    private val repository = FirebaseAppointmentRepositoryImpl(mockk(relaxed = true))

    @Test
    fun `appointment toMap should correctly convert to Map`() {
        val appointment = Appointment(
            id = "apt1",
            doctorId = "doc123",
            patientId = "pat456",
            scheduledAt = LocalDateTime.of(2025, 8, 28, 14, 0),
            status = AppointmentStatus.SCHEDULED
        )

        val map = repository.run { appointment.toMap() }

        assertEquals("apt1", map["id"])
        assertEquals("doc123", map["doctorId"])
        assertEquals("pat456", map["patientId"])
        assertEquals("2025-08-28T14:00", map["scheduledAt"])
        assertEquals("SCHEDULED", map["status"])
    }

    @Test
    fun `document snapshot toDomain should correctly convert to Appointment`() {
        val snapshot = mockk<DocumentSnapshot>()
        every { snapshot.id } returns "apt1"
        every { snapshot.data } returns mapOf(
            "doctorId" to "doc123",
            "patientId" to "pat456",
            "scheduledAt" to "2025-08-28T14:00",
            "status" to "COMPLETED"
        )

        val appointment = repository.run { snapshot.toDomain() }

        assertNotNull(appointment)
        assertEquals("apt1", appointment?.id)
        assertEquals("doc123", appointment?.doctorId)
        assertEquals("pat456", appointment?.patientId)
        assertEquals(LocalDateTime.of(2025, 8, 28, 14, 0), appointment?.scheduledAt)
        assertEquals(AppointmentStatus.COMPLETED, appointment?.status)
    }

    @Test
    fun `document snapshot toDomain should default to SCHEDULED on invalid status`() {
        val snapshot = mockk<DocumentSnapshot>()
        every { snapshot.id } returns "apt2"
        every { snapshot.data } returns mapOf(
            "doctorId" to "doc123",
            "patientId" to "pat456",
            "scheduledAt" to "2025-08-28T14:00",
            "status" to "INVALID_STATUS"
        )

        val appointment = repository.run { snapshot.toDomain() }

        assertNotNull(appointment)
        assertEquals(AppointmentStatus.SCHEDULED, appointment?.status)
    }
}
