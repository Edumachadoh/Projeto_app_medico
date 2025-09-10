package com.up.clinica_digital.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.common.HasId
import com.up.clinica_digital.domain.repository.ICrudRepository
import kotlinx.coroutines.tasks.await

abstract class FirebaseCrudRepository<T : HasId>(
    private val collectionPath: String,
    private val firestore: FirebaseFirestore
) : ICrudRepository<T> {

    protected val collection = firestore.collection(collectionPath)

    abstract fun T.toMap(): Map<String, Any>
    abstract fun DocumentSnapshot.toDomain(): T?

    override suspend fun create(item: T): Boolean = try {
        collection.document(item.id).set(item.toMap()).await()
        true
    } catch (e: Exception) { false }

    override suspend fun list(): List<T> = try {
        collection.get().await().documents.mapNotNull { it.toDomain() }
    } catch (e: Exception) { emptyList() }

    override suspend fun getById(id: String): T? = try {
        collection.document(id).get().await().toDomain()
    } catch (e: Exception) { null }

    override suspend fun update(item: T): Boolean = try {
        collection.document(item.id).update(item.toMap()).await()
        true
    } catch (e: Exception) { false }

    override suspend fun delete(id: String): Boolean = try {
        collection.document(id).delete().await()
        true
    } catch (e: Exception) { false }
}