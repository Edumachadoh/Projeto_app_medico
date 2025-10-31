package com.up.clinica_digital.domain.repository

import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.LoginResult
import com.up.clinica_digital.domain.model.Patient

//Defines the contract for handling user authentication (login, registration, session)
interface UserAuthRepository {

    //Attempts to log in a user. Returns LoginResult (ID, Role) on success, on null on failure
    suspend fun login(email: String, password: String): LoginResult?

    //Registers a new Doctor account. Returns the new user's ID on success, or null on failure
    suspend fun registerDoctor(doctor: Doctor): String?

    //Registers a new Patient account. Returns the new user's ID on success, or null on failure
    suspend fun registerPatient(patient: Patient): String?

    //Clears the current user's session data (logs them out)
    suspend fun logout()

    //Gets the ID of the currently logged-in user. Returns null if no user is signed in
    fun currentUserId(): String?
}