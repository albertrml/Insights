package br.com.arml.insights.viewmodel.insert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.repository.NoteRepository
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.data.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertViewModel @Inject constructor(
    private val tagRepository: TagRepository,
    private val noteRepository: NoteRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(InsertUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTags()
    }

    fun onEvent(event: InsertUiEvent){
        when(event) {
            is InsertUiEvent.OnInsertTag -> {
                event.apply { insertTag(name,color,description) }
            }
            is InsertUiEvent.OnInsertInsight -> {
                event.apply { insertNote(title,body,situation,tagName) }
            }
            is InsertUiEvent.OnGetTags -> { getTags() }
        }
    }

    private fun getTags(){
        viewModelScope.launch {
            tagRepository.getAll().collectLatest { response ->
                response.update(_uiState){ state, res ->
                    if (res is Response.Success)
                        state.copy(tags = res.result)
                    else
                        state.copy(tags = emptyList())
                }
            }
        }
    }

    private fun insertTag(name: String, color: String, description: String){
        viewModelScope.launch {
            val tag = Tag(name = name, color = color, description = description)
            tagRepository.insert(tag).collect{ response ->
                response.update(_uiState){ state, res ->
                    state.copy( insertTagState = res )
                }
            }
        }
    }

    private fun insertNote(name: String, body: String, situation: String, tagName:String){
        viewModelScope.launch {
            val note = Note(
                title = name,
                body = body,
                situation = situation,
                creationDate = System.currentTimeMillis(),
                tag = tagName,
            )
            noteRepository.insert(note).collect{ response ->
                response.update(_uiState){ state, res ->
                    state.copy( insertNoteState = res )
                }
            }
        }
    }
}