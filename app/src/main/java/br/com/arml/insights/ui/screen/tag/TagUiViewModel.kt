package br.com.arml.insights.ui.screen.tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arml.insights.domain.TagUiUseCase
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.data.mapTo
import br.com.arml.insights.utils.data.update
import br.com.arml.insights.utils.exception.InsightException.TagAlreadyExistsException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagUiViewModel @Inject constructor(private val tagUiUseCase: TagUiUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(TagUiState())
    val uiState = _uiState.asStateFlow()

    init {
        retrieveTagUi(sortBy = { it.name })
    }

    fun onEvent(event: TagUiEvent) {
        when (event) {
            is TagUiEvent.OnDeleteTagUi -> {
                deleteTagUi(event.tagUi)
            }

            is TagUiEvent.OnEditTagUi -> {
                editTagUi(event.tagUi)
            }

            is TagUiEvent.OnInsertTagUi -> {
                insertTagUi(event.newTagUi)
            }

            is TagUiEvent.OnRetrieveTagUi -> {
                retrieveTagUi(sortBy = { it.name })
            }

            is TagUiEvent.OnSearchTagsUi -> {
                searchTagUi(event.query)
            }
        }
    }

    private fun deleteTagUi(tagUi: TagUi) {
        viewModelScope.launch {
            tagUiUseCase.deleteTagUi(tagUi).collect { response ->
                response.update(_uiState) { state, res ->
                    state.copy(deleteState = res)
                }
            }
        }
    }

    private fun editTagUi(tagUi: TagUi) {
        viewModelScope.launch {
            tagUiUseCase.updateTagUi(tagUi).collect { response ->
                response.update(_uiState) { state, res ->
                    state.copy(editState = res)
                }
            }
        }
    }

    private fun insertTagUi(tagUi: TagUi) {
        viewModelScope.launch {
            if (tagUiUseCase.isTagNameExists(tagUi.name)) {
                _uiState.update { state ->
                    state.copy(insertState = Response.Failure(TagAlreadyExistsException()))
                }
            } else {
                tagUiUseCase.insertTagUi(tagUi).collect { response ->
                    response.update(_uiState) { state, res ->
                        state.copy(insertState = res)
                    }
                }
            }
        }
    }

    private inline fun <T : Comparable<T>> retrieveTagUi(
        crossinline sortBy: (TagUi) -> T
    ) {
        viewModelScope.launch {
            tagUiUseCase.fetchTagUi().collect { response ->
                response.mapTo { tagUi -> tagUi.sortedBy(sortBy) }
                    .update(_uiState) { state, res ->
                        state.copy(retrieveState = res)
                    }
            }
        }
    }

    private fun searchTagUi(query: String) {
        viewModelScope.launch {
            tagUiUseCase.searchTagByName(query).collect { response ->
                response.update(_uiState) { state, res ->
                    state.copy(
                        searchState = if (res is Response.Success) res.result else emptyList()
                    )
                }
            }
        }
    }
}