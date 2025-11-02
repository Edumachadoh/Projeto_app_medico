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

// Displays a single topic in detail, including its comments
// Receives the topicId from the navigation route and calls the screen
// Observes the states via viewModel.topicUiState

@Composable
// display a single forum topic.
fun TopicItemScreen(
    // Receives the NavController to manage navigation (e.g., going back).
    navController: NavController,
    // Receives the ID of the topic to be loaded
    topicId: String?,
    // Gets an instance of the ForumViewModel
    viewModel: ForumViewModel = hiltViewModel()
) {
    // Used to trigger "side effects" (like network calls) safely.
    // Ensures we only try to load a topic if a valid ID was provided.
    LaunchedEffect(topicId) {
        if (topicId != null) {
            // Calls the function in the ViewModel to fetch the topic data.
            viewModel.loadTopic(topicId)
        }
    }

    // "Observes" the state (topicUiState) from the ViewModel.
    val topicUiState by viewModel.topicUiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        // Sets the navigation bar at the top of the screen.
        topBar = { TopNavigationBar(navController) }
    ) { paddingValues ->
        when (val state = topicUiState) {
            // Case 1: If the state is 'Loading' (loading data).
            is TopicUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    // Shows a spinning loading icon.
                    CircularProgressIndicator()
                }
            }
            // Case 2: If the state is 'Error' (an error occurred).
            is TopicUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(state.message, color = Color.Red)
                }
            }
            // Case 3: If the state is 'Success' (data loaded successfully).
            is TopicUiState.Success -> {
                // Extracts the 'topic' object from the success state.
                val topic = state.topic
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

                Column(modifier = Modifier.padding(16.dp).padding(paddingValues)) {
                    Text(
                        // Displays the topic title in bold and large style.
                        text = topic.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        // Displays the metadata: author and formatted creation date.
                        text = "por ${topic.authorId} em ${topic.createdAt.format(formatter)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    // Displays the full content of the topic.
                    Text(text = topic.content, style = MaterialTheme.typography.bodyMedium)

                    // Checks if the topic's comment list is not empty.
                    if (topic.comments.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            // Displays a title for the comments section.
                            text = "Comentários (${topic.comments.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Loops through each 'comment' in the 'topic.comments' list.
                        topic.comments.forEach { comment ->
                            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text(
                                    // Shows the comment author
                                    text = "${comment.authorId}:",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    // Shows the comment content.
                                    text = comment.content,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
            // Case 4: Initial state, can show an indicator or nothing (before any loading).
            is TopicUiState.Idle -> {
            }
        }
    }
}