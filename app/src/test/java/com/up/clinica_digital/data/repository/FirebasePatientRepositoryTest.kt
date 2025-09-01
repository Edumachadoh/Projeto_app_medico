package com.up.clinica_digital.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.time.LocalDate

class FirebasePatientRepositoryTest {

    private val repository = FirebasePatientRepository(mockk(relaxed = true))

    @Test
    fun `patient toMap should correctly convert to Map`() {
        val patient = com.up.clinica_digital.domain.model.Patient(
            id = "p1",
            name = "Davi Agra",
            email = "daviagra@gmail.com",
            cpf = "12345678900",
            passwordHash = "hashed_pw",
            birthDate = LocalDate.of(1990, 1, 1)
        )

        val map = repository.run { patient.toMap() }

        assertEquals("p1", map["id"])
        assertEquals("Davi Agra", map["name"])
        assertEquals("daviagra@gmail.com", map["email"])
        assertEquals("12345678900", map["cpf"])
        assertEquals("hashed_pw", map["passwordHash"])
        assertEquals("1990-01-01", map["birthDate"])
    }

    @Test
    fun `document snapshot toDomain should correctly convert to Patient`() {
        val snapshot = mockk<DocumentSnapshot>()
        every { snapshot.id } returns "p1"
        every { snapshot.data } returns mapOf(
            "name" to "Davi Agra",
            "email" to "daviagra@gmail.com",
            "cpf" to "12345678900",
            "passwordHash" to "hashed_pw",
            "birthDate" to "1990-01-01"
        )

        val patient = repository.run { snapshot.toDomain() }

        assertNotNull(patient)
        assertEquals("p1", patient?.id)
        assertEquals("Davi Agra", patient?.name)
        assertEquals("daviagra@gmail.com", patient?.email)
        assertEquals("12345678900", patient?.cpf)
        assertEquals("hashed_pw", patient?.passwordHash)
        assertEquals(LocalDate.of(1990, 1, 1), patient?.birthDate)
    }
}
