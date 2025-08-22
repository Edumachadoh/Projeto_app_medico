package com.up.clinica_digital.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ForumComment(
    val id: String,
    val topicId: String,
    val authorId: String,
    val content: String,
    val createdAt: LocalDateTime
) : Parcelable