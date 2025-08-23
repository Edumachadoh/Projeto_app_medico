package com.up.clinica_digital.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.up.clinica_digital.domain.interfaces.repository.IUserAuthRepository
import kotlinx.coroutines.tasks.await

class FirebaseUserAuthRepository(
    private val auth: FirebaseAuth
) : IUserAuthRepository {

    override suspend fun login(email: String, password: String): String? = try {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        result.user?.uid
    } catch (e: Exception) {
        null
    }

    override suspend fun register(email: String, password: String): String? = try {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        result.user?.uid
    } catch (e: Exception) {
        null
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override fun currentUserId(): String? = auth.currentUser?.uid
}