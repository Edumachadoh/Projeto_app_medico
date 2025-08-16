package com.up.clinica_digital.models

import androidx.room.Entity
import androidx.room.PrimaryKey

open class User(
    open val id: String,
    open val name: String,
    open val email: String
)