package com.up.clinica_digital.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Appointment(
    override val id: String,
    val doctorId: String,
    val patientId: String,
    val scheduledAt: LocalDateTime,
    val status: AppointmentStatus
) : HasId, Parcelable