package com.up.clinica_digital.domain.usecase

import com.up.clinica_digital.domain.common.HasId
import com.up.clinica_digital.domain.repository.ICrudRepository

class UpdateEntityUseCase<T: HasId>(
    private val repository: ICrudRepository<T>
) {
    suspend fun invoke(entity: T): Boolean = repository.update(entity)
}