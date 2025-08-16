package com.up.clinica_digital.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "forum_comments",
    foreignKeys = [
        ForeignKey(
            entity = ForumTopic::class,
            parentColumns = ["id"],
            childColumns = ["topicId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["topicId"])]
)
data class ForumComment(
    @PrimaryKey val id: String,
    val topicId: String,
    val authorId: String,
    val content: String,
    val createdAt: String
)