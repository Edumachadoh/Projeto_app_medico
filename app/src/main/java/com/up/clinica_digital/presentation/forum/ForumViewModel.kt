package com.up.clinica_digital.presentation.forum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.ForumComment
import com.up.clinica_digital.domain.model.ForumTopic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ForumViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<ForumUiState>(ForumUiState.Loading)
    val uiState: StateFlow<ForumUiState> = _uiState.asStateFlow()

    // Estado para a tela de detalhes do tópico
    private val _topicUiState = MutableStateFlow<TopicUiState>(TopicUiState.Idle)
    val topicUiState: StateFlow<TopicUiState> = _topicUiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var allTopics = listOf<ForumTopic>()

    init {
        loadMockForumTopics()
    }

    private fun loadMockForumTopics() {
        viewModelScope.launch {
            _uiState.value = ForumUiState.Loading
            delay(500) // Simula busca na rede

            val commentsCaso1 = listOf(
                ForumComment("c1", "t1", "Dr.Carlos.Cardio", "Interessante. ECG sem supra de ST, mas a clínica é soberana. Já considerou marcadores cardíacos seriados?", LocalDateTime.now().minusHours(10)),
                ForumComment("c2", "t1", "Ana.Gastro", "Pela descrição da dor em queimação que melhora ao sentar, não podemos descartar causa esofágica. Um IBP de teste poderia ser uma opção.", LocalDateTime.now().minusHours(8)),
                ForumComment("c3", "t1", "Dr.Silva.GP", "Obrigado, colegas. Os marcadores vieram negativos na primeira coleta. Vou aguardar a segunda e iniciar IBP. Agradeço as sugestões.", LocalDateTime.now().minusHours(5))
            )

            val commentsCaso2 = listOf(
                ForumComment("c4", "t2", "Mariana.Endocrino", "Tenho visto bons resultados com a semaglutida, mas o custo ainda é um fator limitante para muitos pacientes no SUS.", LocalDateTime.now().minusDays(1)),
                ForumComment("c5", "t2", "Lucas.Nefro", "Importante ressaltar os benefícios renais dos iSGLT2 nesse perfil de paciente. A dapagliflozina tem se mostrado excelente na redução da progressão da DRC.", LocalDateTime.now().minusHours(18))
            )

            allTopics = listOf(
                ForumTopic(
                    id = "t1",
                    title = "Discussão de Caso: Paciente 47 anos com dor torácica atípica",
                    content = "Homem, 47 anos, sem comorbidades conhecidas, apresenta-se com dor torácica retroesternal em queimação há 2 dias, intermitente, sem irradiação. Nega dispneia. ECG inicial sem alterações agudas. Exame físico normal. Qual seria a abordagem diagnóstica inicial dos colegas?",
                    authorId = "Dr.Silva.GP",
                    createdAt = LocalDateTime.now().minusDays(1),
                    comments = commentsCaso1
                ),
                ForumTopic(
                    id = "t2",
                    title = "Novas diretrizes no tratamento de DM2 com alto risco cardiovascular",
                    content = "A nova diretriz da SBC recomenda o uso de análogos de GLP-1 ou iSGLT2 como segunda linha após metformina para pacientes com DM2 e alto risco CV. Quais as experiências de vocês na prática clínica diária? Quais as principais barreiras?",
                    authorId = "Juliana.Cardio",
                    createdAt = LocalDateTime.now().minusDays(2),
                    comments = commentsCaso2
                ),
                ForumTopic(
                    id = "t3",
                    title = "Manejo de Burnout na Residência Médica",
                    content = "Gostaria de abrir um espaço para discutir um tema que infelizmente é muito presente: o esgotamento profissional durante a residência. Quais estratégias vocês utilizam ou viram funcionar para lidar com a pressão e manter a saúde mental?",
                    authorId = "Rafael.Residente",
                    createdAt = LocalDateTime.now().minusHours(8),
                    comments = listOf(
                        ForumComment("c6", "t3", "Psic.Helena", "Ótima iniciativa, Rafael. É fundamental criar redes de apoio e normalizar a busca por ajuda psicológica. Muitos hospitais oferecem serviços de suporte.", LocalDateTime.now().minusHours(2))
                    )
                ),
                ForumTopic(
                    id = "t4",
                    title = "Dermatose de difícil diagnóstico em paciente pediátrico",
                    content = "Paciente de 5 anos com lesões papulares e pruriginosas nos membros inferiores há 3 semanas, sem resposta a corticoides tópicos de média potência. Hx de atopia familiar. Alguém teria alguma sugestão ou já viu um caso semelhante?",
                    authorId = "Beatriz.Pedia",
                    createdAt = LocalDateTime.now().minusDays(3),
                    comments = emptyList()
                )
            )
            _uiState.value = ForumUiState.Success(allTopics)
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterTopics(query)
    }

    private fun filterTopics(query: String) {
        val filteredList = if (query.isBlank()) {
            allTopics
        } else {
            allTopics.filter { topic ->
                topic.title.contains(query, ignoreCase = true) ||
                        topic.content.contains(query, ignoreCase = true)
            }
        }
        _uiState.value = ForumUiState.Success(filteredList)
    }

    fun loadTopic(topicId: String) {
        viewModelScope.launch {
            _topicUiState.value = TopicUiState.Loading
            val topic = allTopics.find { it.id == topicId }
            if (topic != null) {
                _topicUiState.value = TopicUiState.Success(topic)
            } else {
                _topicUiState.value = TopicUiState.Error("Tópico não encontrado")
            }
        }
    }
}