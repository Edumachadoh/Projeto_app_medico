package com.up.clinica_digital.domain.common

//CAUE: The "common" package inside the "domain" layer contains shared or reusable elements
//The "HasId" interface is a simple contract that defines that any object implementing it
//must have a unique identifier property called "id" (of type String).
//For example, classes like "Appointment", "Doctor", or "Patient" can implement "HasId"

interface HasId {
    val id: String
}