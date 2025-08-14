package com.example.clinica_digital_curitiba.model

class Doctor(
    id: String,
    name: String,
    email: String,
    val crm: String,
    val rqe: String,
    val specialization: String
) : User(id, name, email)