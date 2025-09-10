package com.up.clinica_digital.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.model.ForumComment
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class FirebaseForumCommentRepositoryIT {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var repository: FirebaseForumCommentRepositoryImpl

    @Before
    fun setup() {
        firestore = FirebaseFirestore.getInstance().apply {
            useEmulator("10.0.2.2", 8080)
        }
        repository = FirebaseForumCommentRepositoryImpl(firestore)
    }

    @Test
    fun crudOperations_workCorrectly() = runBlocking {
        val comment = ForumComment(
            id = "c1",
            topicId = "t1",
            authorId = "author1",
            content = "This is a test comment",
            createdAt = LocalDateTime.now()
        )

        assertTrue(repository.create(comment))

        val fetched = repository.getById("c1")
        assertNotNull(fetched)
        assertEquals("This is a test comment", fetched?.content)

        val updated = comment.copy(content = "Updated comment")
        assertTrue(repository.update(updated))
        val fetchedAgain = repository.getById("c1")
        assertEquals("Updated comment", fetchedAgain?.content)

        val all = repository.list()
        assertTrue(all.any { it.id == "c1" })

        assertTrue(repository.delete("c1"))
        assertNull(repository.getById("c1"))
    }
}
