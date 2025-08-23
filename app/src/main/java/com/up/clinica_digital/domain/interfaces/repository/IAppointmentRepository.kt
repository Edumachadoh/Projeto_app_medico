package com.up.clinica_digital.domain.interfaces.repository

import com.up.clinica_digital.domain.model.Appointment

interface IAppointmentRepository : ICrudRepository<Appointment> {
    suspend fun listByDoctor(doctorId: String): List<Appointment>
    suspend fun listByPatient(patientId: String): List<Appointment>
}