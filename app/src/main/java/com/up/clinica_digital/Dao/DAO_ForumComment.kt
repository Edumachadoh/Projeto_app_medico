package com.up.clinica_digital.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.up.clinica_digital.models.Doctor
import com.up.clinica_digital.models.ForumComment

@Dao
interface ForumCommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: ForumComment)

    @Query("SELECT * FROM forum_comments WHERE topicId = :topicId")
    suspend fun getCommentsByTopic(topicId: String): List<ForumComment>

    @Query("SELECT * FROM forum_comments WHERE id = :id")
    suspend fun getCommentById(id: String): ForumComment?

    @Query("SELECT * FROM forum_comments")
    suspend fun getAllForumComments(): List<ForumComment>

    @Delete
    suspend fun deleteComment(comment: ForumComment)
}