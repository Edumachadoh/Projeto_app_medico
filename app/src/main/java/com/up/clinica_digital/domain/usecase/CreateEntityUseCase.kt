package com.up.clinica_digital.domain.usecase

import com.up.clinica_digital.domain.common.HasId
import com.up.clinica_digital.domain.repository.CrudRepository

class CreateEntityUseCase<T: HasId>(
    private val repository: CrudRepository<T>
) {
    suspend fun invoke(entity: T): Boolean = repository.create(entity)
}