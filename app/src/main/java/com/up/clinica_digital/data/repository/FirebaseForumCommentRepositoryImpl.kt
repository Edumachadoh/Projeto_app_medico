package com.up.clinica_digital.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.model.ForumComment
import com.up.clinica_digital.domain.repository.ForumCommentRepository
import java.time.LocalDateTime

// ANA: Firebase implementation for medical forum comment management
class FirebaseForumCommentRepositoryImpl(
    firestore: FirebaseFirestore
) : FirebaseCrudRepositoryImpl<ForumComment>("forumComments", firestore), ForumCommentRepository {

    // ANA: Converts forum comment to Firestore data format with topic reference
    override fun ForumComment.toMap(): Map<String, Any> = mapOf(
        "id" to id,
        "topicId" to topicId,
        "authorId" to authorId,
        "content" to content,
        "createdAt" to createdAt.toString()
    )

    // ANA: Converts Firestore document back to forum comment object
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