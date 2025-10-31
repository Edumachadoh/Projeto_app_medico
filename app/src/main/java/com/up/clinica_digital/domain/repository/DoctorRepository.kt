package com.up.clinica_digital.domain.repository

import com.up.clinica_digital.domain.model.Doctor

//Dfines all data operations for Doctors
//Herds basic save(), delete(), find() functions
interface DoctorRepository : CrudRepository<Doctor> {

    //Finds doctors bu their state (UF) and speciality
    suspend fun listByUFAndSpeciality(uf: String, speciality: String): List<Doctor>
}