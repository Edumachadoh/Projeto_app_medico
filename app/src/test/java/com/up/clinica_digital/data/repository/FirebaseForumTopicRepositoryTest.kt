package com.up.clinica_digital.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.time.LocalDateTime

class FirebaseForumTopicRepositoryTest {

    private val repository = FirebaseForumTopicRepository(mockk(relaxed = true))

    @Test
    fun `forum topic toMap should correctly convert to Map`() {
        val topic = com.up.clinica_digital.domain.model.ForumTopic(
            id = "t1",
            title = "Quero Eliminar 20 kg de Fezes",
            content = "E vc ta endauldi agrummgit e vc tá endauldiE vc tá endauldi agrummgit ag os e riuboissrummgit",
            authorId = "u123",
            createdAt = LocalDateTime.of(2025, 8, 31, 15, 0),
            comments = emptyList()
        )

        val map = repository.run { topic.toMap() }

        assertEquals("t1", map["id"])
        assertEquals("Quero Eliminar 20 kg de Fezes", map["title"])
        assertEquals("E vc ta endauldi agrummgit e vc tá endauldiE vc tá endauldi agrummgit ag os e riuboissrummgit", map["content"])
        assertEquals("u123", map["authorId"])
        assertEquals("2025-08-31T15:00", map["createdAt"])
    }

    @Test
    fun `document snapshot toDomain should correctly convert to ForumTopic`() {
        val snapshot = mockk<DocumentSnapshot>()
        every { snapshot.id } returns "t1"
        every { snapshot.data } returns mapOf(
            "title" to "Quero Eliminar 20 kg de Fezes",
            "content" to "E vc ta endauldi agrummgit e vc tá endauldiE vc tá endauldi agrummgit ag os e riuboissrummgit",
            "authorId" to "u123",
            "createdAt" to "2025-08-31T15:00"
        )

        val topic = repository.run { snapshot.toDomain() }

        assertNotNull(topic)
        assertEquals("t1", topic?.id)
        assertEquals("Quero Eliminar 20 kg de Fezes", topic?.title)
        assertEquals("E vc ta endauldi agrummgit e vc tá endauldiE vc tá endauldi agrummgit ag os e riuboissrummgit", topic?.content)
        assertEquals("u123", topic?.authorId)
        assertEquals(LocalDateTime.of(2025, 8, 31, 15, 0), topic?.createdAt)
        assertEquals(emptyList<Any>(), topic?.comments)
    }
}