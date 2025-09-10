package com.up.clinica_digital.domain.usecase

import com.up.clinica_digital.domain.common.HasId
import com.up.clinica_digital.domain.repository.CrudRepository

class GetEntityByIdUseCase<T: HasId>(
    private val repository: CrudRepository<T>
) {
    suspend fun invoke(id: String): T? = repository.getById(id)
}