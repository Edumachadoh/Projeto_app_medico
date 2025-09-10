package com.up.clinica_digital.domain.usecase

import com.up.clinica_digital.domain.common.HasId
import com.up.clinica_digital.domain.repository.ICrudRepository

class ListEntitiesUseCase<T: HasId>(
    private val repository: ICrudRepository<T>
) {
    suspend fun invoke(): List<T> = repository.list()
}