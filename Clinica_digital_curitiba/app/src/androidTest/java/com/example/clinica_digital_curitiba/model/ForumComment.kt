package com.example.clinica_digital_curitiba.model

import java.time.LocalDateTime

class ForumComment(
    val id: String,
    val topicId: String,
    val authorId: String,
    val content: String,
    val createdAt: LocalDateTime
)