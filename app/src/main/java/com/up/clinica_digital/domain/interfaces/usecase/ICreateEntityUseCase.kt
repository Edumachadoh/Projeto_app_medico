package com.up.clinica_digital.domain.interfaces.usecase

import com.up.clinica_digital.domain.common.HasId

interface ICreateEntityUseCase<T: HasId> {
    suspend operator fun invoke(entity: T): Boolean
}