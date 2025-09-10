package com.up.clinica_digital.domain.usecase

import com.up.clinica_digital.domain.common.HasId
import com.up.clinica_digital.domain.repository.ICrudRepository
import com.up.clinica_digital.domain.interfaces.usecase.IGetEntityByIdUseCase

class GetEntityByIdUseCase<T: HasId>(
    private val repository: ICrudRepository<T>
) : IGetEntityByIdUseCase<T> {
    override suspend fun invoke(id: String): T? = repository.getById(id)
}