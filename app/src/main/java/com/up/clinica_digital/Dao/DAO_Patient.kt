package com.up.clinica_digital.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.up.clinica_digital.models.Patient

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: Patient)

    @Query("SELECT * FROM Patient WHERE id = :id")
    suspend fun getPatientById(id: String): Patient?

    @Query("SELECT * FROM Patient")
    suspend fun getAllPatients(): List<Patient>

    @Update
    suspend fun updatePatient(patient: Patient)

    @Delete
    suspend fun deletePatient(patient: Patient)
}