package com.up.clinica_digital.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.model.ForumComment
import com.up.clinica_digital.domain.repository.IForumCommentRepository
import java.time.LocalDateTime

class FirebaseForumCommentRepository(
    firestore: FirebaseFirestore
) : FirebaseCrudRepository<ForumComment>("forumComments", firestore), IForumCommentRepository {

    override fun ForumComment.toMap(): Map<String, Any> = mapOf(
        "id" to id,
        "topicId" to topicId,
        "authorId" to authorId,
        "content" to content,
        "createdAt" to createdAt.toString()
    )

    override fun DocumentSnapshot.toDomain(): ForumComment? {
        val data = data ?: return null
        return ForumComment(
            id = id,
            topicId = data["topicId"] as? String ?: "",
            authorId = data["authorId"] as? String ?: "",
            content = data["content"] as? String ?: "",
            createdAt = LocalDateTime.parse(data["createdAt"] as? String ?: LocalDateTime.now().toString()),
        )
    }
}