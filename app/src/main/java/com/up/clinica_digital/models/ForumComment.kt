package com.up.clinica_digital.models

import java.time.LocalDateTime

class ForumComment (
    val id: String,
    val topicId: String,
    val authorId: String,
    val content: String,
    val createdAt: LocalDateTime
)