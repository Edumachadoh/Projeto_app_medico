package com.up.clinica_digital.domain.repository

import com.up.clinica_digital.domain.model.Patient

//Defines all data operations for Patients
//Inherits all basic save(), delete(), find() functions from CrudRepository
interface PatientRepository : CrudRepository<Patient>
//No custom functions are needed for comments beyond standard CRUD, meanwhile DoctorRepository has a list function