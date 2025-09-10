package com.up.clinica_digital.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.up.clinica_digital.domain.model.Doctor
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class FirebaseDoctorRepositoryTest {

    private val repository = FirebaseDoctorRepositoryImpl(mockk(relaxed = true))

    @Test
    fun `doctor toMap should correctly convert to Map`() {
        val doctor = Doctor(
            id = "123",
            name = "Dr. Ana",
            email = "ana@gmail.com",
            cpf = "11122233344",
            passwordHash = "hashed_pw",
            crm = "CRM1234",
            rqe = "RQE5678",
            specialization = "Alergista",
            uf = "SC"
        )

        val map = repository.run { doctor.toMap() }

        assertEquals("123", map["id"])
        assertEquals("Dr. Ana", map["name"])
        assertEquals("ana@gmail.com", map["email"])
        assertEquals("CRM1234", map["crm"])
        assertEquals("Alergista", map["specialization"])
    }

    @Test
    fun `document snapshot toDomain should correctly convert to Doctor`() {
        val snapshot = mockk<DocumentSnapshot>()
        every { snapshot.id } returns "123"
        every { snapshot.data } returns mapOf(
            "name" to "Dr. Pedro",
            "email" to "pedro@gmail.com",
            "cpf" to "55566677788",
            "passwordHash" to "hashed_strange",
            "crm" to "CRM9876",
            "rqe" to "RQE4321",
            "specialization" to "Neurologista",
            "uf" to "SP"
        )

        val doctor = repository.run { snapshot.toDomain() }

        assertNotNull(doctor)
        assertEquals("123", doctor?.id)
        assertEquals("Dr. Pedro", doctor?.name)
        assertEquals("CRM9876", doctor?.crm)
        assertEquals("SP", doctor?.uf)
    }
}
