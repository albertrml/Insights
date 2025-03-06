package br.com.arml.insights.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.repository.NoteRepository
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
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnDeleteNote -> { deleteNote(event.note) }
            is HomeUiEvent.OnFetchNotesByCreationDateInAscendingOrder -> {
                fetchNotesByCreationDate()
            }
            is HomeUiEvent.OnFetchNotesByCreationDateInDescendingOrder -> {
                fetchNotesByCreationDate(true)
            }
            is HomeUiEvent.OnFetchNotesBySituationsInAscendingOrder -> {
                fetchNotesBySituation()
            }
            is HomeUiEvent.OnFetchNotesBySituationsInDescendingOrder -> {
                fetchNotesBySituation(true)
            }
            is HomeUiEvent.OnFetchNotesByTagInAscendingOrder -> {
                fetchNotesByTag()
            }
            is HomeUiEvent.OnFetchNotesByTagInDescendingOrder -> {
                fetchNotesByTag(true)
            }
            is HomeUiEvent.OnFetchNotesByTitleInAscendingOrder -> {
                fetchNotesByTitle()
            }
            is HomeUiEvent.OnFetchNotesByTitleInDescendingOrder -> {
                fetchNotesByTitle(true)
            }
        }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.delete(note).collect { response ->
                response.update(_uiState) { state, res ->
                    state.copy(deleteNoteState = res)
                }
            }
        }
    }

    private inline fun <T : Comparable<T>> fetchNotes(
        crossinline sortBy: (Note) -> T,
        isReverse: Boolean = false
    ) {
        viewModelScope.launch {
            noteRepository.getAll().collect { response ->
                response.mapTo { notes ->
                    if (isReverse) notes.sortedByDescending(sortBy)
                    else notes.sortedBy(sortBy)
                }.update(_uiState) { state, res ->
                    state.copy(fetchNotesState = res)
                }
            }
        }
    }

    private fun fetchNotesByCreationDate(isReverse: Boolean = false) {
        fetchNotes(sortBy = { it.creationDate }, isReverse = isReverse)
    }

    private fun fetchNotesByTitle(isReverse: Boolean = false) {
        fetchNotes(sortBy = { it.title }, isReverse = isReverse)
    }

    private fun fetchNotesBySituation(isReverse: Boolean = false) {
        fetchNotes(sortBy = { it.situation }, isReverse = isReverse)
    }

    private fun fetchNotesByTag(isReverse: Boolean = false) {
        fetchNotes(sortBy = { it.tag }, isReverse = isReverse)
    }

}