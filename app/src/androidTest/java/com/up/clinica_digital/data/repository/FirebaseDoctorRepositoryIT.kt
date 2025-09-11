package com.up.clinica_digital.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.model.Doctor
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirebaseDoctorRepositoryIT {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var repository: FirebaseDoctorRepositoryImpl

    @Before
    fun setup() {
        firestore = FirebaseFirestore.getInstance().apply {
            useEmulator("10.0.2.2", 8080)
        }
        repository = FirebaseDoctorRepositoryImpl(firestore)
    }

    @Test
    fun crudOperations_workCorrectly() = runBlocking {
        val doctor = Doctor(
            id = "d1",
            name = "Dr. Integration",
            email = "integration@test.com",
            cpf = "00011122233",
            passwordHash = "hash",
            crm = "CRM123",
            rqe = "RQE123",
            specialization = "Ortopedia",
            uf = "SP"
        )

        assertTrue(repository.create(doctor))

        val fetched = repository.getById("d1")
        assertNotNull(fetched)
        assertEquals("Dr. Integration", fetched?.name)

        val updated = doctor.copy(name = "Dr. Updated")
        assertTrue(repository.update(updated))
        val fetchedAgain = repository.getById("d1")
        assertEquals("Dr. Updated", fetchedAgain?.name)

        val all = repository.list()
        assertTrue(all.any { it.id == "d1" })

        assertTrue(repository.delete("d1"))
        assertNull(repository.getById("d1"))
    }
}