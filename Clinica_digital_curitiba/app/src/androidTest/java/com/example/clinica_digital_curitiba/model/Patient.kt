package com.example.clinica_digital_curitiba.model

import java.time.LocalDate

class Patient (
    id: String,
    name: String,
    email: String,
    val birthDate: LocalDate
) : User(id, name, email)