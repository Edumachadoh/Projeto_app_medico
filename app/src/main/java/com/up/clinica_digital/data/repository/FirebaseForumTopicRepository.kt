package com.up.clinica_digital.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.model.ForumTopic
import com.up.clinica_digital.domain.repository.ForumTopicRepository
import java.time.LocalDateTime

class FirebaseForumTopicRepository(
    firestore: FirebaseFirestore
) : FirebaseCrudRepository<ForumTopic>("forumTopics", firestore), ForumTopicRepository {

    override fun ForumTopic.toMap(): Map<String, Any> = mapOf(
        "id" to id,
        "title" to title,
        "content" to content,
        "authorId" to authorId,
        "createdAt" to createdAt.toString()
    )

    override fun DocumentSnapshot.toDomain(): ForumTopic? {
        val data = data ?: return null
        return ForumTopic(
            id = id,
            title = data["title"] as? String ?: "",
            content = data["content"] as? String ?: "",
            authorId = data["authorId"] as? String ?: "",
            createdAt = LocalDateTime.parse(this["createdAt"] as? String ?: LocalDateTime.now().toString()),
            comments = emptyList()
        )
    }
}
