package com.up.clinica_digital.presentation.forum

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.up.clinica_digital.presentation.component.top_nav.TopNavigationBar
import java.time.format.DateTimeFormatter

@Composable
fun TopicItemScreen(
    navController: NavController,
    topicId: String?,
    viewModel: ForumViewModel = hiltViewModel()
) {
    LaunchedEffect(topicId) {
        if (topicId != null) {
            viewModel.loadTopic(topicId)
        }
    }

    val topicUiState by viewModel.topicUiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = { TopNavigationBar(navController) }
    ) { paddingValues ->
        when (val state = topicUiState) {
            is TopicUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is TopicUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(state.message, color = Color.Red)
                }
            }
            is TopicUiState.Success -> {
                val topic = state.topic
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

                Column(modifier = Modifier.padding(16.dp).padding(paddingValues)) {
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
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        topic.comments.forEach { comment ->
                            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text(
                                    text = "${comment.authorId}:",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = comment.content,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
            is TopicUiState.Idle -> {
                // Estado inicial, pode mostrar um indicador ou nada
            }
        }
    }
}