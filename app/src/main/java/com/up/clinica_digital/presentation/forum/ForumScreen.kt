package com.up.clinica_digital.presentation.forum

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.up.clinica_digital.domain.model.ForumTopic
import java.time.format.DateTimeFormatter

@Composable
fun ForumScreen(
    viewModel: ForumViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Campo de busca
            OutlinedTextField(
                value = viewModel.searchQuery.value,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Buscar no Fórum") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // Gerenciamento de estado da UI
            when (val state = uiState) {
                is ForumUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is ForumUiState.Error -> {
                    Text(state.message, color = Color.Red)
                }
                is ForumUiState.Success -> {
                    // Lista de Tópicos
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.topics) { topic ->
                            TopicItem(topic = topic)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopicItem(topic: ForumTopic) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = topic.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "por ${topic.authorId} em ${topic.createdAt.format(formatter)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = topic.content, style = MaterialTheme.typography.bodyMedium)

            if (topic.comments.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Comentários (${topic.comments.size})",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                topic.comments.forEach { comment ->
                    Text(
                        text = "${comment.authorId}: ${comment.content}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}