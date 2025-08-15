package com.up.clinica_digital.models

import java.time.LocalDate

class Patient (
    id: String,
    name: String,
    email: String,
    val birthDate: LocalDate
) : User(id, name, email)