package com.up.clinica_digital.domain.model

//Defines the finite set of possible states for an appointment
enum class AppointmentStatus {
    SCHEDULED,
    COMPLETED,
    CONFIRMED,
    CANCELED
}