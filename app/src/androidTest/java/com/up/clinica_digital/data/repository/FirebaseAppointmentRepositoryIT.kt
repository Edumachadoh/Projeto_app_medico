package com.up.clinica_digital.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.model.Appointment
import com.up.clinica_digital.domain.model.AppointmentStatus
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class FirebaseAppointmentRepositoryIT {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var repository: FirebaseAppointmentRepository

    @Before
    fun setup() {
        firestore = FirebaseFirestore.getInstance().apply {
            useEmulator("10.0.2.2", 8080)
        }
        repository = FirebaseAppointmentRepository(firestore)
    }

    @Test
    fun crudOperations_workCorrectly() = runBlocking {
        val appointment = Appointment(
            id = "a1",
            doctorId = "d1",
            patientId = "p1",
            scheduledAt = LocalDateTime.now().plusDays(1),
            status = AppointmentStatus.SCHEDULED
        )

        assertTrue(repository.create(appointment))

        val fetched = repository.getById("a1")
        assertNotNull(fetched)
        assertEquals(AppointmentStatus.SCHEDULED, fetched?.status)

        val updated = appointment.copy(status = AppointmentStatus.CONFIRMED)
        assertTrue(repository.update(updated))
        val fetchedAgain = repository.getById("a1")
        assertEquals(AppointmentStatus.CONFIRMED, fetchedAgain?.status)

        val all = repository.list()
        assertTrue(all.any { it.id == "a1" })

        assertTrue(repository.delete("a1"))
        assertNull(repository.getById("a1"))
    }
}
