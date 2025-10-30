package com.up.clinica_digital.domain.model

import android.os.Parcelable
import com.up.clinica_digital.domain.common.HasId
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

// Automatically generates the code needed to pass this object between screens
@Parcelize

//Data model representing a medical appointment
data class Appointment(
    override val id: String, //Unique identifier from HasId
    val doctorId: String, //Doctor's foreign key
    val patientId: String, //Patient's foreign key
    val scheduledAt: LocalDateTime, //Date and time of the appointment
    val status: AppointmentStatus //Status of the appointment (SCHEDULED, COMPLETED, CONFIRMED, CANCELED)
) : HasId, Parcelable //Implements: HasId (contract for objects with an 'id') and Parcelable (contract for passing data)