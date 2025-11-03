package com.up.clinica_digital.domain.usecase

import com.up.clinica_digital.domain.common.HasId
import com.up.clinica_digital.domain.repository.CrudRepository

//The "usecase" package defines the business logic of the application —
//each class here represents a specific operation that can be performed in the domain layer.
//In this case, the package includes a set of *generic CRUD use cases*,
//These classes provide reusable operations.

//This CRUD use case setup helps keep the code organized and reusable.
//It means that all entities (like Appointment, Doctor, or Patient)
//can share the same basic operations — create, read, update, delete —
//without needing to write the same logic multiple times.

class CreateEntityUseCase<T: HasId>(
    private val repository: CrudRepository<T>
) {
    suspend fun invoke(entity: T): Boolean = repository.create(entity)
}