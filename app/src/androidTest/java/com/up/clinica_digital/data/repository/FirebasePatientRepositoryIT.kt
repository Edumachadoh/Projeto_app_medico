package com.up.clinica_digital.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.model.Patient
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class FirebasePatientRepositoryIT {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var repository: FirebasePatientRepository

    @Before
    fun setup() {
        firestore = FirebaseFirestore.getInstance().apply {
            useEmulator("10.0.2.2", 8080)
        }
        repository = FirebasePatientRepository(firestore)
    }

    @Test
    fun crudOperations_workCorrectly() = runBlocking {
        val patient = Patient(
            id = "p1",
            name = "Integration Patient",
            email = "patient@test.com",
            cpf = "99911122233",
            passwordHash = "hash",
            birthDate = LocalDate.of(1990, 5, 10)
        )

        assertTrue(repository.create(patient))

        val fetched = repository.getById("p1")
        assertNotNull(fetched)
        assertEquals("Integration Patient", fetched?.name)

        val updated = patient.copy(name = "Patient Updated")
        assertTrue(repository.update(updated))
        val fetchedAgain = repository.getById("p1")
        assertEquals("Patient Updated", fetchedAgain?.name)

        val all = repository.list()
        assertTrue(all.any { it.id == "p1" })

        assertTrue(repository.delete("p1"))
        assertNull(repository.getById("p1"))
    }
}
