package com.up.clinica_digital.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.domain.model.ForumTopic
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class FirebaseForumTopicRepositoryIT {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var repository: FirebaseForumTopicRepositoryImpl

    @Before
    fun setup() {
        firestore = FirebaseFirestore.getInstance().apply {
            useEmulator("10.0.2.2", 8080)
        }
        repository = FirebaseForumTopicRepositoryImpl(firestore)
    }

    @Test
    fun crudOperations_workCorrectly() = runBlocking {
        val topic = ForumTopic(
            id = "t1",
            title = "Integration Topic",
            content = "This is a test topic",
            authorId = "author1",
            createdAt = LocalDateTime.now(),
            comments = emptyList()
        )

        assertTrue(repository.create(topic))

        val fetched = repository.getById("t1")
        assertNotNull(fetched)
        assertEquals("Integration Topic", fetched?.title)

        val updated = topic.copy(title = "Updated Topic")
        assertTrue(repository.update(updated))
        val fetchedAgain = repository.getById("t1")
        assertEquals("Updated Topic", fetchedAgain?.title)

        val all = repository.list()
        assertTrue(all.any { it.id == "t1" })

        assertTrue(repository.delete("t1"))
        assertNull(repository.getById("t1"))
    }
}
