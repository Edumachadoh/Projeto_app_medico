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

// Acts as the brain for the forum screens
// Manages the states. _uiState for the main screen and _topicUiState for topic details
// Data source via (Mock), a function that simulates data from an API
// Business logic using onSearchQueryChange, filterTopics to contain the logic for listing and filtering topics
// and loadTopic to find a topic in allTopics

@HiltViewModel
class ForumViewModel @Inject constructor() : ViewModel() {

    // stores the state (Loading, Success, Error)
    private val _uiState = MutableStateFlow<ForumUiState>(ForumUiState.Loading)
    // The UI (Composable) observes this Flow to react to state changes.
    val uiState: StateFlow<ForumUiState> = _uiState.asStateFlow()


    // State for the Topic Details Screen (TopicItemScreen)
    // _topicUiState: Private StateFlow for the state of a *single* topic
    private val _topicUiState = MutableStateFlow<TopicUiState>(TopicUiState.Idle)
    val topicUiState: StateFlow<TopicUiState> = _topicUiState.asStateFlow()


    //  Search Bar State
    // _searchQuery: Private StateFlow that stores the current search text.
    private val _searchQuery = MutableStateFlow("")
    // Read the search text
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Local "database": In-memory list that stores all topics.
    private var allTopics = mutableListOf<ForumTopic>()

    // Executed once when the ViewModel is created.
    // Starts loading the sample data from loadMockForumTopics

    init {
        loadMockForumTopics()
    }

    // Loads a list of sample topics and comments
    private fun loadMockForumTopics() {
        viewModelScope.launch {
            // Sets the UI state to "Loading".
            _uiState.value = ForumUiState.Loading

            // Mocked data (kept as is)
            val commentsCaso1 = listOf(
                ForumComment("c1", "t1", "Dr.Carlos.Cardio", "Interessante. ECG sem supra de ST, mas a clínica é soberana. Já considerou marcadores cardíacos seriados?", LocalDateTime.now().minusHours(10)),
                ForumComment("c2", "t1", "Ana.Gastro", "Pela descrição da dor em queimação que melhora ao sentar, não podemos descartar causa esofágica. Um IBP de teste poderia ser uma opção.", LocalDateTime.now().minusHours(8)),
                ForumComment("c3", "t1", "Dr.Silva.GP", "Obrigado, colegas. Os marcadores vieram negativos na primeira coleta. Vou aguardar a segunda e iniciar IBP. Agradeço as sugestões.", LocalDateTime.now().minusHours(5))
            )
            val commentsCaso2 = listOf(
                ForumComment("c4", "t2", "Mariana.Endocrino", "Tenho visto bons resultados com a semaglutida, mas o custo ainda é um fator limitante para muitos pacientes no SUS.", LocalDateTime.now().minusDays(1)),
                ForumComment("c5", "t2", "Lucas.Nefro", "Importante ressaltar os benefícios renais dos iSGLT2 nesse perfil de paciente. A dapagliflozina tem se mostrado excelente na redução da progressão da DRC.", LocalDateTime.now().minusHours(18))
            )
            // Initializes the mutable list
            allTopics = mutableListOf(
                ForumTopic("t1", "Discussão de Caso: Paciente 47 anos com dor torácica atípica", "Homem, 47 anos...", "Dr.Silva.GP", LocalDateTime.now().minusDays(1), commentsCaso1),
                ForumTopic("t2", "Novas diretrizes no tratamento de DM2 com alto risco cardiovascular", "A nova diretriz da SBC...", "Juliana.Cardio", LocalDateTime.now().minusDays(2), commentsCaso2),
                ForumTopic("t3", "Manejo de Burnout na Residência Médica", "Gostaria de abrir um espaço...", "Rafael.Residente", LocalDateTime.now().minusHours(8), listOf(ForumComment("c6", "t3", "Psic.Helena", "Ótima iniciativa...", LocalDateTime.now().minusHours(2)))),
                ForumTopic("t4", "Dermatose de difícil diagnóstico em paciente pediátrico", "Paciente de 5 anos...", "Beatriz.Pedia", LocalDateTime.now().minusDays(3), emptyList())
            )
            // Sets the UI state to "Success", passing the list of topics.
            _uiState.value = ForumUiState.Success(allTopics)
        }
    }

    // Public function called by the UI (ForumScreen) whenever the user changes the text in the search field.
    // filterTopics applies the filter to the list of topics.
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterTopics(query)
    }

    // Filters the 'allTopics' list based on the search 'query'.
    private fun filterTopics(query: String) {
        // If the search is blank, uses the complete list.
        val filteredList = if (query.isBlank()) {
            allTopics
        } else {
            // Otherwise, filters the 'allTopics' list
            allTopics.filter { topic ->
                // The search criterion is: the title OR the content contains the search text.
                topic.title.contains(query, ignoreCase = true) ||
                        topic.content.contains(query, ignoreCase = true)
            }
        }
        // Updates the UI state (ForumUiState) with the new filtered list.
        _uiState.value = ForumUiState.Success(filteredList)
    }

    //  Loads the details of a specific topic.
    fun loadTopic(topicId: String) {
        viewModelScope.launch {
            // Sets the details screen state to "Loading".
            _topicUiState.value = TopicUiState.Loading
            // Searches for the topic in the in-memory list.
            val topic = allTopics.find { it.id == topicId }

            // If the topic is found, updates the state to "Success".
            // If not found, updates the state to "Error".
            if (topic != null) {
                _topicUiState.value = TopicUiState.Success(topic)
            } else {
                _topicUiState.value = TopicUiState.Error("Tópico não encontrado")
            }
        }
    }


}