package com.up.clinica_digital.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Patient(
    @PrimaryKey override val id: String,
    override val name: String,
    override val email: String,
    val birthDate: String
) : User(id, name, email)
