package com.up.clinica_digital.presentation.forum

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.up.clinica_digital.domain.model.ForumTopic
import java.time.format.DateTimeFormatter

// Reusable component that defines how a list item looks on the Forum-Screen

@Composable
// It shows the title, author, date, and a content preview in a clickable Card.
fun TopicItem(
    topic: ForumTopic,
    onTopicClick: () -> Unit
) {
    // Defines the formatting pattern for the date and time.
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    Card(
        // The Modifier makes the Card occupy the full available width.
        modifier = Modifier.fillMaxWidth(),
        // Defines the action to be executed when the card is clicked.
        onClick = onTopicClick
    ) {
        // Organizes the internal elements vertically, one below the other.
        Column(modifier = Modifier.padding(16.dp)) {
            // Displays the topic title.
            Text(
                text = topic.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Displays author and creation date.
            Text(
                text = "por ${topic.authorId} em ${topic.createdAt.format(formatter)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            // Displays a preview of the topic content, the first 120 characters
            Text(text = topic.content.take(120).plus("..."), style = MaterialTheme.typography.bodyMedium)

        }
    }
}