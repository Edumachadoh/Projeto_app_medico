package com.up.clinica_digital.domain.usecase

import com.up.clinica_digital.domain.common.HasId
import com.up.clinica_digital.domain.interfaces.repository.ICrudRepository
import com.up.clinica_digital.domain.interfaces.usecase.IDeleteEntityUseCase

class DeleteEntityUseCase<T: HasId>(
    private val repository: ICrudRepository<T>
) : IDeleteEntityUseCase<T> {
    override suspend fun invoke(id: String): Boolean = repository.delete(id)
}