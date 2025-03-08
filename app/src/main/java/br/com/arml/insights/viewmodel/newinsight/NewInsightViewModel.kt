package br.com.arml.insights.viewmodel.newinsight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.repository.NoteRepository
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.utils.data.update
import br.com.arml.insights.viewmodel.newinsight.NewInsightUiEvent.OnFetchTags
import br.com.arml.insights.viewmodel.newinsight.NewInsightUiEvent.OnInsertInsight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewInsightViewModel @Inject constructor(
    private val tagRepository: TagRepository,
    private val noteRepository: NoteRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(NewInsightUiState())
    val uiState: StateFlow<NewInsightUiState> = _uiState.asStateFlow()

    init {
        fetchTags()
    }

    fun onEvent(event : NewInsightUiEvent){
        when(event){
            is OnFetchTags -> { fetchTags() }
            is OnInsertInsight -> {
                insertNote(
                    title = event.title,
                    situation = event.situation,
                    body = event.body,
                    tagId = event.tagId
                )
            }
        }
    }

    private fun fetchTags(){
        viewModelScope.launch {
            tagRepository.getAll().collect{ response ->
                response.update(_uiState){ state, res ->
                    state.copy(fetchTags = res)
                }
            }
        }
    }

    private fun insertNote(
        title: String,
        situation: String,
        body: String,
        tagId: Int
    ){
        viewModelScope.launch {
            val newNote = Note(
                title = title,
                situation = situation,
                body = body,
                creationDate = System.currentTimeMillis(),
                tagId = tagId
            )
            noteRepository.insert(newNote).collect{ response ->
                response.update(_uiState){ state, res ->
                    state.copy(insertInsightState = res)
                }
            }
        }
    }

}