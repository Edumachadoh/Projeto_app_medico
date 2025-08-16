package com.up.clinica_digital.relation_models

import androidx.room.Embedded
import androidx.room.Relation
import com.up.clinica_digital.models.ForumComment
import com.up.clinica_digital.models.ForumTopic


data class ForumTopicComment(
    @Embedded val topic: ForumTopic,
    @Relation(
        parentColumn = "id",
        entityColumn = "topicId"
    )
    val comments: List<ForumComment>
)
