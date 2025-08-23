package com.up.clinica_digital.domain.interfaces.usecase

import com.up.clinica_digital.domain.common.HasId

interface IListEntitiesUseCase<T: HasId> {
    suspend operator fun invoke(): List<T>
}