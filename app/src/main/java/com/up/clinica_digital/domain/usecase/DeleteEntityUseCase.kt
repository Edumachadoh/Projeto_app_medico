package com.up.clinica_digital.domain.usecase

import com.up.clinica_digital.domain.common.HasId
import com.up.clinica_digital.domain.repository.CrudRepository

class DeleteEntityUseCase<T: HasId>(
    private val repository: CrudRepository<T>
) {
    suspend fun invoke(id: String): Boolean = repository.delete(id)
}