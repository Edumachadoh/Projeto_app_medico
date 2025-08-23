package com.up.clinica_digital.domain.interfaces.usecase

import com.up.clinica_digital.domain.common.HasId

interface IDeleteEntityUseCase<T: HasId> {
    suspend operator fun invoke(id: String): Boolean
}