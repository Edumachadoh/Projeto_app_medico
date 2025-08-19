package com.up.clinica_digital.controllers

import com.up.clinica_digital.data.AppDatabase
import com.up.clinica_digital.models.Patient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PatientController(private val db: AppDatabase) {

    fun getPatients(patientList: MutableList<Patient>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val patientsFromDb = db.patientDao().getAllPatients()
                withContext(Dispatchers.Main) {
                    patientList.clear()
                    patientList.addAll(patientsFromDb)
                }
            } catch (e: Exception) {
            }
        }
    }

    fun insertPatient(patient: Patient) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                db.patientDao().insertPatient(patient)
            } catch (e: Exception) {
            }
        }
    }
}