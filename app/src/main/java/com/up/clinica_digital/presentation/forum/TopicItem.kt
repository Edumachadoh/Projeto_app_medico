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
// Ele mostra o título, autor, data e uma prévia do conteúdo em um Card clicável.
fun TopicItem(
    topic: ForumTopic,
    onTopicClick: () -> Unit
) {
    // Define o padrão de formatação para a data e hora.
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    Card(
        // O Modifier faz o Card ocupar toda a largura disponível.
        modifier = Modifier.fillMaxWidth(),
        // Define a ação a ser executada quando o card for clicado.
        onClick = onTopicClick
    ) {
        // Organiza os elementos internos verticalmente, um abaixo do outro.
        Column(modifier = Modifier.padding(16.dp)) {
            // Exibe o título do tópico.
            Text(
                text = topic.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Exibe autor e data de criação.
            Text(
                text = "por ${topic.authorId} em ${topic.createdAt.format(formatter)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            // Exibe uma prévia do conteúdo do tópico., os primeiros 120 caracteres
            Text(text = topic.content.take(120).plus("..."), style = MaterialTheme.typography.bodyMedium)

        }
    }
}