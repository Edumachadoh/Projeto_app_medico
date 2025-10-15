package com.up.clinica_digital.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.model.ForumTopic
import com.up.clinica_digital.domain.repository.ForumTopicRepository
import java.time.LocalDateTime

// ANA: Firebase implementation for medical forum topic management
class FirebaseForumTopicRepositoryImpl(
    firestore: FirebaseFirestore
) : FirebaseCrudRepositoryImpl<ForumTopic>("forumTopics", firestore), ForumTopicRepository {

    // ANA: Converts forum topic to Firestore data format with author info
    override fun ForumTopic.toMap(): Map<String, Any> = mapOf(
        "id" to id,
        "title" to title,
        "content" to content,
        "authorId" to authorId,
        "createdAt" to createdAt.toString()
    )

    // ANA: Converts Firestore document back to forum topic object
    override fun DocumentSnapshot.toDomain(): ForumTopic? {
        val data = data ?: return null
        return ForumTopic(
            id = id,
            title = data["title"] as? String ?: "",
            content = data["content"] as? String ?: "",
            authorId = data["authorId"] as? String ?: "",
            createdAt = LocalDateTime.parse(data["createdAt"] as? String ?: LocalDateTime.now().toString()),
            comments = emptyList()
        )
    }
}