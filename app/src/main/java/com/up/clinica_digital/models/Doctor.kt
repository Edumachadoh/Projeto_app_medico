package com.up.clinica_digital.models
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Doctor(
    @PrimaryKey override val id: String,
    override val name: String,
    override val email: String,
    val crm: String,
    val rqe: String,
    val specialization: String
) : User(id, name, email)