package com.up.clinica_digital.domain.model

import android.os.Parcelable
import com.up.clinica_digital.domain.common.HasId
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ForumComment(
    override val id: String,
    val topicId: String,
    val authorId: String,
    val content: String,
    val createdAt: LocalDateTime
) : HasId, Parcelable