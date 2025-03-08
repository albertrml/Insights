package br.com.arml.insights.viewmodel.updatetag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.data.update
import br.com.arml.insights.utils.exception.InsightException.TagAlreadyExistsException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateTagViewModel @Inject constructor(
    private val tagRepository: TagRepository,
) : ViewModel(){

    private val _uiState = MutableStateFlow(UpdateTagUiState())
    val uiState: StateFlow<UpdateTagUiState> = _uiState.asStateFlow()

    fun onEvent(event: UpdateTagUiEvent){
        when(event){
            is UpdateTagUiEvent.OnUpdateTag -> {
                event.apply { updateTag(tagId, newName,newDescription,newColor) }
            }
        }
    }

    private fun updateTag(tagId: Int, newName: String, newDescription: String, newColor: String){
        viewModelScope.launch {
            if (tagRepository.isTagNameExists(newName)){
                _uiState.update { state ->
                    state.copy( updateTagState = Response.Failure(TagAlreadyExistsException()) )
                }
            }
            else{
                val newTag = Tag(
                    id = tagId,
                    name = newName,
                    description = newDescription,
                    color = newColor
                )
                viewModelScope.launch {
                    tagRepository.update(newTag).collect{ response ->
                        response.update(_uiState){ state, res ->
                            state.copy( updateTagState = res )
                        }
                    }
                }
            }
        }
    }

}