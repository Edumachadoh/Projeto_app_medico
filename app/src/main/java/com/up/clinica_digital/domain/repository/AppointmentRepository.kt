package com.up.clinica_digital.domain.repository

import com.up.clinica_digital.domain.model.Appointment

//Interface that defines the data operations for the Appointment model
//Gets standard functions like create, read, update, delete
interface AppointmentRepository : CrudRepository<Appointment> {

    //Returns a list of appointments for a specific doctor
    suspend fun listByDoctor(doctorId: String): List<Appointment>

    //Returns a list of appointments for a specific patient
    suspend fun listByPatient(patientId: String): List<Appointment>
}