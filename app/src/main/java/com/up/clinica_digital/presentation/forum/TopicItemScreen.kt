package com.up.clinica_digital.presentation.forum

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import com.up.clinica_digital.presentation.component.top_nav.TopNavigationBar
import com.up.clinica_digital.presentation.navigation.Screen
import java.time.format.DateTimeFormatter

@Composable
fun TopicItemScreen(
    viewModel: ForumViewModel = hiltViewModel(),
    navController: NavController
) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    val uiState by viewModel.uiState.collectAsState()

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
            Scaffold(
                modifier = Modifier.fillMaxWidth(),
                topBar = { TopNavigationBar(navController) }
            ) { paddingValues ->
                Column(modifier = Modifier.padding(16.dp). padding(paddingValues)) {
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
    }

}