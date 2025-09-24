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

    override suspend fun login(email: String, password: String): LoginResult? {
        return try {
            if (!isValidEmail(email.trim())) return null

            val doctorQuery = firestore.collection("doctors")
                .whereEqualTo("email", email.trim())
                .get()
                .await()

            if (!doctorQuery.isEmpty) {
                val doctorDoc = doctorQuery.documents[0]
                val storedPassword = doctorDoc.getString("passwordHash")

                if (storedPassword == password) {
                    val firebaseUid = signInOrCreateFirebaseUser(email.trim(), password)

                    return if (firebaseUid != null) {
                        LoginResult(userId = firebaseUid, role = UserRole.DOCTOR)
                    } else {
                        LoginResult(userId = doctorDoc.id, role = UserRole.DOCTOR)
                    }
                }
            }

            val patientQuery = firestore.collection("patients")
                .whereEqualTo("email", email.trim())
                .get()
                .await()

            if (!patientQuery.isEmpty) {
                val patientDoc = patientQuery.documents[0]
                val storedPassword = patientDoc.getString("passwordHash")

                if (storedPassword == password) {
                    val firebaseUid = signInOrCreateFirebaseUser(email.trim(), password)

                    return if (firebaseUid != null) {
                        LoginResult(userId = firebaseUid, role = UserRole.PATIENT)
                    } else {
                        LoginResult(userId = patientDoc.id, role = UserRole.PATIENT)
                    }
                }
            }

            null
        } catch (e: Exception) {
            println("Login error: ${e.message}")
            null
        }
    }

    private suspend fun signInOrCreateFirebaseUser(email: String, password: String): String? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user?.uid
        } catch (signInException: Exception) {
            try {
                val createResult = auth.createUserWithEmailAndPassword(email, password).await()
                createResult.user?.uid
            } catch (createException: Exception) {
                null
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
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