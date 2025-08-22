package com.up.clinica_digital.domain.repository

import com.up.clinica_digital.domain.common.HasId

interface CrudRepository<T: HasId> {
    suspend fun create(item: T): Boolean
    suspend fun list(): List<T>
    suspend fun getById(id: String): T?
    suspend fun update(item: T): Boolean
    suspend fun delete(id: String): Boolean
}