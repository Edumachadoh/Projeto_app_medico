package com.up.clinica_digital.controllers

import com.up.clinica_digital.data.AppDatabase
import com.up.clinica_digital.models.Doctor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DoctorController(private val db: AppDatabase) {

    fun getDoctors(doctorList: MutableList<Doctor>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val doctorsFromDb = db.doctorDao().getAllDoctors()
                withContext(Dispatchers.Main) {
                    doctorList.clear()
                    doctorList.addAll(doctorsFromDb)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun insertDoctor(doctor: Doctor) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                db.doctorDao().insertDoctor(doctor)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateDoctor(doctor: Doctor) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                db.doctorDao().updateDoctor(doctor)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteDoctor(doctor: Doctor) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                db.doctorDao().deleteDoctor(doctor)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
