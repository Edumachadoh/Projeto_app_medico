package com.up.clinica_digital.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.common.HasId
import com.up.clinica_digital.domain.repository.CrudRepository
import kotlinx.coroutines.tasks.await

// ANA: Base class for all Firebase repositories providing common CRUD operations
// This is called a Generic Class and it can be implemented with any class as
// long as said class has the HasId interface (this is why we got the <T : HasId>).
abstract class FirebaseCrudRepositoryImpl<T : HasId>(
    private val collectionPath: String,
    private val firestore: FirebaseFirestore
) : CrudRepository<T> {

    protected val collection = firestore.collection(collectionPath)

    // ANA: Converts domain object to Firestore map format
    abstract fun T.toMap(): Map<String, Any>

    // ANA: Converts Firestore document back to domain object
    abstract fun DocumentSnapshot.toDomain(): T?

    // ANA: Creates new document in Firestore with item data
    override suspend fun create(item: T): Boolean = try {
        collection.document(item.id).set(item.toMap()).await()
        true
    } catch (e: Exception) { false }

    // ANA: Retrieves all documents from Firestore collection
    override suspend fun list(): List<T> = try {
        collection.get().await().documents.mapNotNull { it.toDomain() }
    } catch (e: Exception) { emptyList() }

    // ANA: Gets single document by ID from Firestore
    override suspend fun getById(id: String): T? = try {
        collection.document(id).get().await().toDomain()
    } catch (e: Exception) { null }

    // ANA: Updates existing document in Firestore
    override suspend fun update(item: T): Boolean = try {
        collection.document(item.id).update(item.toMap()).await()
        true
    } catch (e: Exception) { false }

    // ANA: Deletes document from Firestore by ID
    override suspend fun delete(id: String): Boolean = try {
        collection.document(id).delete().await()
        true
    } catch (e: Exception) { false }
}