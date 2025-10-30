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

@Composable
fun ForumScreen(
    viewModel: ForumViewModel = hiltViewModel(),
    navController: NavController
) {

    // armazena o estado da UI (Loading, Success, Error) do ViewModel.
    val uiState by viewModel.uiState.collectAsState()

    // armazena o texto atual da barra de busca do ViewModel.
    val searchQuery by viewModel.searchQuery.collectAsState()

    // layout que organiza a tela
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize() // Preenche todo o tamanho da tela
                .padding(paddingValues) // Aplica o padding interno do Scaffold
                .padding(horizontal = 16.dp, vertical = 8.dp) // Padding customizado
        ) {
            // Campo de busca
            // notifica o ViewModel quando o texto muda e define o texto do placeholder "Buscar no Fórum"
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Buscar no Fórum") },
                modifier = Modifier.fillMaxWidth()
            )

            // Espaçamento vertical e Titulo da tela
            Spacer(Modifier.height(16.dp))
            Text("Forum", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            // Gerenciamento de estado da UI
            // 'when' é usado para renderizar diferentes UIs com base no 'uiState'.
            when (val state = uiState) {
                // Estado 1: Carregando
                // Exibe um indicador de progresso no centro da tela.
                is ForumUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                // Estado 2: Erro
                // Exibe a mensagem de erro em vermelho.
                is ForumUiState.Error -> {
                    Text(state.message, color = Color.Red)
                }

                // Estado 3: Sucesso (Dados carregados)
                // Lista rolavel por meio de LazyColumn
                is ForumUiState.Success -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Itera sobre a lista de tópicos (vinda do 'state') e cria um item para cada.
                        items(state.topics) { topic ->
                            // Renderiza o Composable 'TopicItem' (definido em outro arquivo).
                            TopicItem(topic = topic, onTopicClick = {
                                // Define a ação de clique: navegar para os detalhes do tópico.
                                navController.navigate(
                                    Screen.TopicItem.createRoute(
                                        // Cria a rota de navegação passando o ID do tópico.
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