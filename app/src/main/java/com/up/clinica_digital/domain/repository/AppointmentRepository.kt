package com.up.clinica_digital.domain.repository

import com.up.clinica_digital.domain.model.Appointment

interface AppointmentRepository : CrudRepository<Appointment> {
    suspend fun listByDoctor(doctorId: String): List<Appointment>
    suspend fun listByPatient(patientId: String): List<Appointment>
}