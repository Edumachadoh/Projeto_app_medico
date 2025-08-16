package com.up.clinica_digital.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "forum_topics")
data class ForumTopic(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val authorId: String,
    val createdAt: String
)