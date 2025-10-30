package com.up.clinica_digital.domain.model

import android.os.Parcelable
import com.up.clinica_digital.domain.common.HasId
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

// Automatically generates the code needed to pass this object between screens
@Parcelize

//Data model for a comment in the forum
data class ForumComment(
    override val id: String, //Unique identifier (from HasId)
    val topicId: String, //Foreign key for the parent ForumTopic
    val authorId: String, //Foreign key for the user who wrote the comment
    val content: String, //The text content of the comment
    val createdAt: LocalDateTime //Timestamp when the comment was posted
) : HasId, Parcelable //Implements: ID contract (HasId) and data passing (Parcelable)