package com.up.clinica_digital.models

class Doctor (
    id: String,
    name: String,
    email: String,
    val crm: String,
    val rqe: String,
    val specialization: String
) : User(id, name, email)