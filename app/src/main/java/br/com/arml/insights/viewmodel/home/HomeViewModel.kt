package br.com.arml.insights.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.utils.data.mapTo
import br.com.arml.insights.utils.data.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TagRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchTags(sortBy = { it.name })
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnDeleteTag -> { deleteNote(event.tag) }
            is HomeUiEvent.OnFetchTagsByColorInAscendingOrder -> {
                fetchTags( sortBy = { it.color } )
            }
            is HomeUiEvent.OnFetchTagsByNameInAscendingOrder -> {
                fetchTags( sortBy = { it.name } )
            }
        }
    }

    private fun deleteNote(tag: Tag) {
        viewModelScope.launch {
            repository.delete(tag).collect { response ->
                response.update(_uiState) { state, res ->
                    state.copy(deleteTagState = res)
                }
            }
        }
    }

    private inline fun <T : Comparable<T>> fetchTags(
        crossinline sortBy: (Tag) -> T
    ) {
        viewModelScope.launch {
            repository.getAll().collect { response ->
                response.mapTo { tags ->
                    tags.sortedBy(sortBy)
                }.update(_uiState) { state, res ->
                    state.copy(fetchTagsState = res)
                }
            }
        }
    }

}