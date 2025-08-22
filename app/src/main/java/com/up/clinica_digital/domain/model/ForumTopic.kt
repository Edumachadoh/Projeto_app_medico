package com.up.clinica_digital.domain.model

import android.os.Parcelable
import com.up.clinica_digital.domain.common.HasId
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ForumTopic(
    override val id: String,
    val title: String,
    val content: String,
    val authorId: String,
    val createdAt: LocalDateTime,
    val comments: List<ForumComment>
) : HasId, Parcelable