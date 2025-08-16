package com.up.clinica_digital.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.up.clinica_digital.models.Doctor


@Dao
interface DoctorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctor(doctor: Doctor)

    @Query("SELECT * FROM Doctor WHERE id = :id")
    suspend fun getDoctorById(id: String): Doctor?

    @Query("SELECT * FROM Doctor")
    suspend fun getAllDoctors(): List<Doctor>

    @Delete
    suspend fun deleteDoctor(doctor: Doctor)
}
