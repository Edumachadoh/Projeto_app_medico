package com.up.clinica_digital.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.up.clinica_digital.domain.repository.UserAuthRepository
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.LoginResult
import com.up.clinica_digital.domain.model.Patient
import com.up.clinica_digital.domain.model.UserRole

// ANA: Firebase authentication service handling login and registration for both user types
class FirebaseUserAuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : UserAuthRepository {

    // ANA: Authenticates user and determines if they are doctor or patient
    override suspend fun login(email: String, password: String): LoginResult? {
        return try {
            if (!isValidEmail(email.trim())) return null

            val result = auth.signInWithEmailAndPassword(email.trim(), password).await()
            val uid = result.user?.uid ?: return null

            // ANA: Checks if user exists in doctors collection
            val doctorDoc = firestore.collection("doctors").document(uid).get().await()
            if (doctorDoc.exists()) {
                return LoginResult(userId = uid, role = UserRole.DOCTOR)
            }

            // ANA: Checks if user exists in patients collection
            val patientDoc = firestore.collection("patients").document(uid).get().await()
            if (patientDoc.exists()) {
                return LoginResult(userId = uid, role = UserRole.PATIENT)
            }

            null
        } catch (e: Exception) {
            println("Login error: ${e.message}")
            null
        }
    }

    // ANA: Validates email format using Android's built-in pattern matcher
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // ANA: Registers new doctor with Firebase Auth and stores profile in Firestore
    override suspend fun registerDoctor(doctor: Doctor): String? = try {
        val result = auth.createUserWithEmailAndPassword(doctor.email, doctor.passwordHash).await()
        val uid = result.user?.uid ?: return null

        val doctorWithId = doctor.copy(id = uid)
        firestore.collection("doctors").document(uid).set(doctorWithId).await()

        uid
    } catch (e: Exception) {
        null
    }

    // ANA: Registers new patient with Firebase Auth and stores profile in Firestore
    override suspend fun registerPatient(patient: Patient): String? = try {
        val result = auth.createUserWithEmailAndPassword(patient.email, patient.passwordHash).await()
        val uid = result.user?.uid ?: return null

        val patientWithId = patient.copy(id = uid)
        firestore.collection("patients").document(uid).set(patientWithId).await()

        uid
    } catch (e: Exception) {
        null
    }

    // ANA: Signs out current user from Firebase Auth
    override suspend fun logout() {
        auth.signOut()
    }

    // ANA: Gets currently logged in user's ID from Firebase Auth
    override fun currentUserId(): String? = auth.currentUser?.uid
}