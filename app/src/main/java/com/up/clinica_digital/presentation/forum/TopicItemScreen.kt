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
// exibir um único tópico do fórum.
fun TopicItemScreen(
    // Recebe o NavController para gerenciar a navegação (ex: voltar).
    navController: NavController,
    // Recebe o ID do tópico a ser carregado
    topicId: String?,
    // Obtém uma instância do ForumViewModel
    viewModel: ForumViewModel = hiltViewModel()
) {
    // É usado para disparar "efeitos colaterais" (como chamadas de rede) de forma segura.
    // Garante que só tentamos carregar um tópico se um ID válido foi fornecido.
    LaunchedEffect(topicId) {
        if (topicId != null) {
            // Chama a função no ViewModel para buscar os dados do tópico.
            viewModel.loadTopic(topicId)
        }
    }

    // "Observa" o estado (topicUiState) do ViewModel.
    val topicUiState by viewModel.topicUiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        // Define a barra de navegação no topo da tela.
        topBar = { TopNavigationBar(navController) }
    ) { paddingValues ->
        when (val state = topicUiState) {
            // Caso 1: Se o estado for 'Loading' (carregando dados).
            is TopicUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    // Mostra um ícone de carregamento giratório.
                    CircularProgressIndicator()
                }
            }
            // Caso 2: Se o estado for 'Error' (ocorreu um erro).
            is TopicUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(state.message, color = Color.Red)
                }
            }
            // Caso 3: Se o estado for 'Success' (dados carregados com sucesso).
            is TopicUiState.Success -> {
                // Extrai o objeto 'topic' de dentro do estado de sucesso.
                val topic = state.topic
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

                Column(modifier = Modifier.padding(16.dp).padding(paddingValues)) {
                    Text(
                        // Exibe o título do tópico em negrito e com estilo grande.
                        text = topic.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        // Exibe os metadados: autor e data de criação formatada.
                        text = "por ${topic.authorId} em ${topic.createdAt.format(formatter)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    // Exibe o conteúdo completo do tópico.
                    Text(text = topic.content, style = MaterialTheme.typography.bodyMedium)

                    // Verifica se a lista de comentários do tópico não está vazia.
                    if (topic.comments.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            // Exibe um título para a seção de comentários.
                            text = "Comentários (${topic.comments.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Faz um loop por cada 'comment' na lista 'topic.comments'.
                        topic.comments.forEach { comment ->
                            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text(
                                    // Mostra o autor do comentário
                                    text = "${comment.authorId}:",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    // Mostra o conteúdo do comentário.
                                    text = comment.content,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
            // Caso 4: Estado inicial, pode mostrar um indicador ou nada (antes de qualquer carregamento).
            is TopicUiState.Idle -> {
            }
        }
    }
}