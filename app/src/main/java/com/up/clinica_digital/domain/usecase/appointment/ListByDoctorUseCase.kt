package com.up.clinica_digital.domain.usecase.appointment

import com.up.clinica_digital.domain.repository.AppointmentRepository
import com.up.clinica_digital.domain.model.Appointment

//CAUE: While the generic CRUD use cases handle common operations
//(like creating, reading, updating, or deleting any entity),
//some features require logic specific to a certain type of entity.
//The "appointment" folder exists to group all use cases
//that are specific to the Appointment entity.
//These use cases go beyond the basic CRUD operations and
//focus on behaviors unique to appointments

class ListByDoctorUseCase(
    private val repository: AppointmentRepository
) {
    suspend fun invoke(doctorId: String): List<Appointment> =
        repository.listByDoctor(doctorId)
}