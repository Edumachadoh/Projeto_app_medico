package com.up.clinica_digital.domain.usecase

import com.up.clinica_digital.domain.common.HasId
import com.up.clinica_digital.domain.repository.ICrudRepository
import com.up.clinica_digital.domain.interfaces.usecase.ICreateEntityUseCase

class CreateEntityUseCase<T: HasId>(
    private val repository: ICrudRepository<T>
) : ICreateEntityUseCase<T> {
    override suspend fun invoke(entity: T): Boolean = repository.create(entity)
}