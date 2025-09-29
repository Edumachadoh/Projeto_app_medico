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

@Composable
fun TopicItem(
    topic: ForumTopic,
    onTopicClick: () -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onTopicClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = topic.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                //tem que criar uma função na viewmodel para achar o doutor que fez o post
                //e colocar o nome dele aqui
                text = "por ${topic.authorId} em ${topic.createdAt.format(formatter)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = topic.content.take(120).plus("..."), style = MaterialTheme.typography.bodyMedium)

        }
    }
}