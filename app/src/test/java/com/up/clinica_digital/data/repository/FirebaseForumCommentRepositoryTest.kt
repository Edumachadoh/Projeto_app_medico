package com.up.clinica_digital.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.time.LocalDateTime

class FirebaseForumCommentRepositoryTest {

    private val repository = FirebaseForumCommentRepository(mockk(relaxed = true))

    @Test
    fun `forum comment toMap should correctly convert to Map`() {
        val comment = com.up.clinica_digital.domain.model.ForumComment(
            id = "c1",
            topicId = "t123",
            authorId = "u456",
            content = "As passivas reinam",
            createdAt = LocalDateTime.of(2025, 8, 31, 14, 0)
        )

        val map = repository.run { comment.toMap() }

        assertEquals("c1", map["id"])
        assertEquals("t123", map["topicId"])
        assertEquals("u456", map["authorId"])
        assertEquals("As passivas reinam", map["content"])
        assertEquals("2025-08-31T14:00", map["createdAt"])
    }

    @Test
    fun `document snapshot toDomain should correctly convert to ForumComment`() {
        val snapshot = mockk<DocumentSnapshot>()
        every { snapshot.id } returns "c1"
        every { snapshot.data } returns mapOf(
            "topicId" to "t123",
            "authorId" to "u456",
            "content" to "As passivas reinam",
            "createdAt" to "2025-08-31T14:00"
        )

        val comment = repository.run { snapshot.toDomain() }

        assertNotNull(comment)
        assertEquals("c1", comment?.id)
        assertEquals("t123", comment?.topicId)
        assertEquals("u456", comment?.authorId)
        assertEquals("As passivas reinam", comment?.content)
        assertEquals(LocalDateTime.of(2025, 8, 31, 14, 0), comment?.createdAt)
    }
}
