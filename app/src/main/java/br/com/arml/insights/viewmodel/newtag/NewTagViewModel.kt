package br.com.arml.insights.viewmodel.newtag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.data.update
import br.com.arml.insights.utils.exception.InsightException.TagAlreadyExistsException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewTagViewModel @Inject constructor(
    private val tagRepository: TagRepository,
) : ViewModel(){

    private val _uiState = MutableStateFlow(NewTagUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: NewTagUiEvent){
        when(event) {
            is NewTagUiEvent.OnNewTag -> {
                event.apply { insertTag(name,color,description) }
            }
        }
    }

    private fun insertTag(name: String, color: String, description: String){
        viewModelScope.launch {
            val tag = Tag(name = name, color = color, description = description)
            if(tagRepository.isTagExists(name)) {
                _uiState.update { state ->
                    state.copy(insertTagState = Response.Failure(TagAlreadyExistsException()))
                }
            }
            else{
                tagRepository.insert(tag).collect { response ->
                    response.update(_uiState) { state, res ->
                        state.copy(insertTagState = res)
                    }
                }
            }
        }
    }

}