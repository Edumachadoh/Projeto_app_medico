package com.up.clinica_digital.domain.repository

import com.up.clinica_digital.domain.common.HasId

//Defines a generic contract (template) for basic Create, Read, Update, Delete operations
//This can be reused by any model 'T' (like Appointment, Patient) that implements 'HasId'. T represents any data model
interface CrudRepository<T: HasId> {

    //Saves a new item to the database. Returns true on success
    suspend fun create(item: T): Boolean

    //Retrieves a list of all items of type 'T'
    suspend fun list(): List<T>

    //Finds a single item by its unique ID. Returns null if not found
    suspend fun getById(id: String): T?

    //Updates an existing item in the database. Returns true on success
    suspend fun update(item: T): Boolean

    //Deletes an item by its unique ID. Returns true on success
    suspend fun delete(id: String): Boolean
}