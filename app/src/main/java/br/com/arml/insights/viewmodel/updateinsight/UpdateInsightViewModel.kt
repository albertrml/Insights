package br.com.arml.insights.viewmodel.updateinsight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.repository.NoteRepository
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.utils.data.update
import br.com.arml.insights.viewmodel.updateinsight.UpdateInsightUiEvent.OnFetchInsight
import br.com.arml.insights.viewmodel.updateinsight.UpdateInsightUiEvent.OnFetchTags
import br.com.arml.insights.viewmodel.updateinsight.UpdateInsightUiEvent.OnUpdateInsight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateInsightViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val tagRepository: TagRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UpdateInsightUiState())
    val uiState = _uiState.asStateFlow()
    
    init {
        fetchTags()
    }
    
    fun onEvent(event: UpdateInsightUiEvent){
        when(event){
            is OnFetchInsight -> { fetchInsight(event.id) }
            is OnFetchTags -> { fetchTags() }
            is OnUpdateInsight -> {}
        }
    }
    
    private fun fetchInsight(noteId: Int){
        viewModelScope.launch { 
            noteRepository.getById(noteId).collect{ response -> 
                response.update(_uiState){ state, res -> 
                    state.copy(fetchInsightState = res)
                }
            }
        }
    }

    private fun fetchTags(){
        viewModelScope.launch {
            tagRepository.getAll().collect { response ->
                response.update(_uiState) { state, res ->
                    state.copy(fetchTagsState = res)
                }
            }
        }
    }

    private fun updateInsight(
        id: Int,
        title: String,
        situation: String,
        body: String,
        creationDate: Long,
        tagId: Int
    ){
        viewModelScope.launch {
            val updateNote = Note(
                id = id,
                title = title,
                situation = situation,
                body = body,
                creationDate = creationDate,
                tagId = tagId
            )
            noteRepository.update(updateNote).collect{ response ->
                response.update(_uiState){ state, res ->
                    state.copy(updateInsightState = res)
                }
            }
        }
    }

}