package com.up.clinica_digital.domain.model

import android.os.Parcelable
import com.up.clinica_digital.domain.common.HasId
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

// Automatically generates the code needed to pass this object between screens
@Parcelize

//Data model for a forum topic
data class ForumTopic(
    override val id: String, //Unique identifier (from HasId)
    val title: String, //Title of the topic
    val content: String, //Content of the topic
    val authorId: String, //Foreign key for the user who created the topic
    val createdAt: LocalDateTime, //Timestamp when the topic was created
    val comments: List<ForumComment> //List of comments in the topic
) : HasId, Parcelable //Implements: ID contract (HasId) and data passing (Parcelable)