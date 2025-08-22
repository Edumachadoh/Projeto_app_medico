package com.up.clinica_digital.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ForumTopic(
    val id: String,
    val title: String,
    val content: String,
    val authorId: String,
    val createdAt: LocalDateTime,
    val comments: List<ForumComment>
) : Parcelable