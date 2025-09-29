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
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ForumViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<ForumUiState>(ForumUiState.Loading)
    val uiState: StateFlow<ForumUiState> = _uiState.asStateFlow()

    private val _topicUiState = MutableStateFlow<TopicUiState>(TopicUiState.Idle)
    val topicUiState: StateFlow<TopicUiState> = _topicUiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var allTopics = mutableListOf<ForumTopic>()

    init {
        loadMockForumTopics()
    }

    private fun loadMockForumTopics() {
        viewModelScope.launch {
            _uiState.value = ForumUiState.Loading

            // Dados mockados (mantidos como estão)
            val commentsCaso1 = listOf(
                ForumComment("c1", "t1", "Dr.Carlos.Cardio", "Interessante. ECG sem supra de ST, mas a clínica é soberana. Já considerou marcadores cardíacos seriados?", LocalDateTime.now().minusHours(10)),
                ForumComment("c2", "t1", "Ana.Gastro", "Pela descrição da dor em queimação que melhora ao sentar, não podemos descartar causa esofágica. Um IBP de teste poderia ser uma opção.", LocalDateTime.now().minusHours(8)),
                ForumComment("c3", "t1", "Dr.Silva.GP", "Obrigado, colegas. Os marcadores vieram negativos na primeira coleta. Vou aguardar a segunda e iniciar IBP. Agradeço as sugestões.", LocalDateTime.now().minusHours(5))
            )
            val commentsCaso2 = listOf(
                ForumComment("c4", "t2", "Mariana.Endocrino", "Tenho visto bons resultados com a semaglutida, mas o custo ainda é um fator limitante para muitos pacientes no SUS.", LocalDateTime.now().minusDays(1)),
                ForumComment("c5", "t2", "Lucas.Nefro", "Importante ressaltar os benefícios renais dos iSGLT2 nesse perfil de paciente. A dapagliflozina tem se mostrado excelente na redução da progressão da DRC.", LocalDateTime.now().minusHours(18))
            )
            // Inicializa a lista mutável
            allTopics = mutableListOf(
                ForumTopic("t1", "Discussão de Caso: Paciente 47 anos com dor torácica atípica", "Homem, 47 anos...", "Dr.Silva.GP", LocalDateTime.now().minusDays(1), commentsCaso1),
                ForumTopic("t2", "Novas diretrizes no tratamento de DM2 com alto risco cardiovascular", "A nova diretriz da SBC...", "Juliana.Cardio", LocalDateTime.now().minusDays(2), commentsCaso2),
                ForumTopic("t3", "Manejo de Burnout na Residência Médica", "Gostaria de abrir um espaço...", "Rafael.Residente", LocalDateTime.now().minusHours(8), listOf(ForumComment("c6", "t3", "Psic.Helena", "Ótima iniciativa...", LocalDateTime.now().minusHours(2)))),
                ForumTopic("t4", "Dermatose de difícil diagnóstico em paciente pediátrico", "Paciente de 5 anos...", "Beatriz.Pedia", LocalDateTime.now().minusDays(3), emptyList())
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