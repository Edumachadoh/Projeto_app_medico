package com.up.clinica_digital.domain.interfaces.usecase

import com.up.clinica_digital.domain.common.HasId

interface IGetEntityByIdUseCase<T: HasId> {
    suspend operator fun invoke(id: String): T?
}