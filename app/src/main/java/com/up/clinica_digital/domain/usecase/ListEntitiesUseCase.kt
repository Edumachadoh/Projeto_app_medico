package com.up.clinica_digital.domain.usecase

import com.up.clinica_digital.domain.common.HasId
import com.up.clinica_digital.domain.interfaces.repository.ICrudRepository
import com.up.clinica_digital.domain.interfaces.usecase.IListEntitiesUseCase

class ListEntitiesUseCase<T: HasId>(
    private val repository: ICrudRepository<T>
) : IListEntitiesUseCase<T> {
    override suspend fun invoke(): List<T> = repository.list()
}