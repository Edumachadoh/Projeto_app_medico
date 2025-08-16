package com.up.clinica_digital.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.up.clinica_digital.models.ForumTopic

@Dao
interface ForumTopicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopic(topic: ForumTopic)

    @Query("SELECT * FROM forum_topics WHERE id = :id")
    suspend fun getTopicById(id: String): ForumTopic?

    @Query("SELECT * FROM forum_topics")
    suspend fun getAllForumTopics(): List<ForumTopic>

    @Delete
    suspend fun deleteTopic(topic: ForumTopic)
}
