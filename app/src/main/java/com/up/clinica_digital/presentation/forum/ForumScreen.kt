package com.up.clinica_digital.presentation.forum

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.up.clinica_digital.presentation.navigation.Screen

// Defines the main list screen for forum topics
// Observes changes in viewModel.uiState to know what to draw on the screen
//

@Composable
fun ForumScreen(
    viewModel: ForumViewModel = hiltViewModel(),
    navController: NavController
) {

    // stores the UI state (Loading, Success, Error) from the ViewModel.
    val uiState by viewModel.uiState.collectAsState()

    // stores the current text from the ViewModel's search bar.
    val searchQuery by viewModel.searchQuery.collectAsState()

    // layout that organizes the screen
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize() // Fills the entire screen size
                .padding(paddingValues) // Applies the internal padding from the Scaffold
                .padding(horizontal = 16.dp, vertical = 8.dp) // Custom padding
        ) {
            // Search field
            // notifies the ViewModel when the text changes and sets the placeholder text "Buscar no Fórum"
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Buscar no Fórum") },
                modifier = Modifier.fillMaxWidth()
            )

            // Vertical spacing and Screen Title
            Spacer(Modifier.height(16.dp))
            Text("Forum", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            // UI state management
            // 'when' is used to render different UIs based on 'uiState'.
            when (val state = uiState) {
                // State 1: Loading
                // Displays a progress indicator in the center of the screen.
                is ForumUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                // State 2: Error
                // Displays the error message in red.
                is ForumUiState.Error -> {
                    Text(state.message, color = Color.Red)
                }

                // State 3: Success (Data loaded)
                // Scrollable list using LazyColumn
                is ForumUiState.Success -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Iterates over the list of topics (from 'state') and creates an item for each.
                        items(state.topics) { topic ->
                            // Renders the 'TopicItem' Composable (defined in another file).
                            TopicItem(topic = topic, onTopicClick = {
                                // Defines the click action: navigate to the topic details.
                                navController.navigate(
                                    Screen.TopicItem.createRoute(
                                        // Creates the navigation route, passing the topic's ID.
                                        topic.id
                                    )
                                )
                            })
                        }
                    }
                }
            }
        }
    }
}