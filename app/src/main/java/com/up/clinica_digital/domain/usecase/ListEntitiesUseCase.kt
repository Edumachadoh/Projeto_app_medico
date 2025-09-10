package com.up.clinica_digital.domain.usecase

import com.up.clinica_digital.domain.common.HasId
import com.up.clinica_digital.domain.repository.CrudRepository

class ListEntitiesUseCase<T: HasId>(
    private val repository: CrudRepository<T>
) {
    suspend fun invoke(): List<T> = repository.list()
}