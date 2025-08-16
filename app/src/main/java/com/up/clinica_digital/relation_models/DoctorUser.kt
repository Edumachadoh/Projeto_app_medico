package com.up.clinica_digital.relation_models

import androidx.room.Embedded
import androidx.room.Relation
import com.up.clinica_digital.models.Doctor
import com.up.clinica_digital.models.User

data class DoctorUser(
    @Embedded val doctor: Doctor,
    @Relation(
        parentColumn = "userId",
        entityColumn = "id"
    )
    val user: User
)