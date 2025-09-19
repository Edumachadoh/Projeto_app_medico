package com.up.clinica_digital.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.up.clinica_digital.domain.repository.UserAuthRepository
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.model.LoginResult
import com.up.clinica_digital.domain.model.UserRole

class FirebaseUserAuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : UserAuthRepository {

    override suspend fun login(email: String, password: String): LoginResult? = try {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val uid = result.user?.uid ?: return null

        val snapshot = firestore.collection("users")
            .document(uid)
            .get()
            .await()

        val roleString = snapshot.getString("role") ?: return null
        val role = UserRole.valueOf(roleString)

        LoginResult(userId = uid, role = role)
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