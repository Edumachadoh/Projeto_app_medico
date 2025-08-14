package com.example.clinica_digital_curitiba.model

import java.time.LocalDateTime

class ForumTopic(
    val id: String,
    val title: String,
    val content: String,
    val authorId: String,
    val createdAt: LocalDateTime,
    val comments: List<ForumComment>
)