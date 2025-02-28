package br.com.arml.insights.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arml.insights.model.repository.NoteRepository
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.utils.data.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tagRepository: TagRepository,
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnFetchNotesByCreationDateInAscendingOrder -> {}
            is HomeUiEvent.OnFetchNotesByCreationDateInDescendingOrder -> {}
            is HomeUiEvent.OnFetchNotesBySituationsInAscendingOrder -> {}
            is HomeUiEvent.OnFetchNotesBySituationsInDescendingOrder -> {}
            is HomeUiEvent.OnFetchNotesByTagInAscendingOrder -> {}
            is HomeUiEvent.OnFetchNotesByTagInDescendingOrder -> {}
            is HomeUiEvent.OnFetchNotesByTitleInAscendingOrder -> {}
            is HomeUiEvent.OnFetchNotesByTitleInDescendingOrder -> {}
        }
    }

    private fun <T,R> fetchNotes(sorted: (T) -> R?, isReverse: Boolean = false) {
        viewModelScope.launch {
            noteRepository.getNotes().collect{ response ->
                response.update(_uiState){ state, res ->
                    state.copy( fetchNotesState = res )
                }
            }
        }
    }


}