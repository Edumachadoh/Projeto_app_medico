package com.up.clinica_digital.relation_models

import androidx.room.Embedded
import androidx.room.Relation
import com.up.clinica_digital.models.Doctor
import com.up.clinica_digital.models.Patient
import com.up.clinica_digital.models.User

data class PatientUser(
    @Embedded val patient: Patient,
    @Relation(
        parentColumn = "userId",
        entityColumn = "id"
    )
    val user: User
)
