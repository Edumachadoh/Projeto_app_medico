package com.up.clinica_digital.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirebaseUserAuthRepositoryIT {

    private lateinit var auth: FirebaseAuth
    private lateinit var repository: FirebaseUserAuthRepositoryImpl

    @Before
    fun setup() {
        auth = FirebaseAuth.getInstance().apply {
            useEmulator("10.0.2.2", 9099)
        }
        repository = FirebaseUserAuthRepositoryImpl(auth)
    }

    @Test
    fun registerLoginLogout_workCorrectly() = runBlocking {
        val email = "integration@test.com"
        val password = "123456"

        val userId = repository.register(email, password)
        assertNotNull(userId)
        assertEquals(userId, repository.currentUserId())

        repository.logout()
        assertNull(repository.currentUserId())

        val loginId = repository.login(email, password)
        assertNotNull(loginId)
        assertEquals(userId, loginId)
        assertEquals(loginId, repository.currentUserId())

        repository.logout()
    }
}