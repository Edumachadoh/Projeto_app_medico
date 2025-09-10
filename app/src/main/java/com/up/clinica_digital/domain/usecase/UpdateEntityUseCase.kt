package com.up.clinica_digital.domain.usecase

import com.up.clinica_digital.domain.common.HasId
import com.up.clinica_digital.domain.repository.ICrudRepository
import com.up.clinica_digital.domain.interfaces.usecase.IUpdateEntityUseCase

class UpdateEntityUseCase<T: HasId>(
    private val repository: ICrudRepository<T>
) : IUpdateEntityUseCase<T> {
    override suspend fun invoke(entity: T): Boolean = repository.update(entity)
}