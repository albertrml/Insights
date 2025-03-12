package br.com.arml.insights.viewmodel.insight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.repository.NoteRepository
import br.com.arml.insights.utils.data.mapTo
import br.com.arml.insights.utils.data.update
import br.com.arml.insights.viewmodel.insight.InsightUiEvent.OnDeleteNote
import br.com.arml.insights.viewmodel.insight.InsightUiEvent.OnFetchNotesByCreationDataInDescendingOrder
import br.com.arml.insights.viewmodel.insight.InsightUiEvent.OnFetchNotesByTitleInAscendingOrder
import br.com.arml.insights.viewmodel.insight.InsightUiEvent.OnFetchNotesBySituationInAscendingOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsightViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(InsightUiState())
    val uiState: StateFlow<InsightUiState> = _uiState.asStateFlow()

    fun onEvent(event: InsightUiEvent){
        when(event) {
            is OnDeleteNote -> { deleteNote(event.note) }
            is OnFetchNotesByCreationDataInDescendingOrder -> {
                fetchNotes(event.idTag,true) { it.creationDate }
            }
            is OnFetchNotesByTitleInAscendingOrder -> {
                fetchNotes(event.idTag) { it.title }
            }
            is OnFetchNotesBySituationInAscendingOrder -> {
                fetchNotes(event.idTag) { it.situation }
            }
        }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.delete(note).collect{ response ->
                response.update(_uiState) { state, res ->
                    state.copy(deleteNoteState = res)
                }
            }
        }
    }

    private inline fun <T : Comparable<T>> fetchNotes(
        idTag: Int,
        isReverse: Boolean = false,
        crossinline sortBy: (Note) -> T
    ) {
        viewModelScope.launch {
            noteRepository.getByTag(idTag).collect { response ->
                response.mapTo { notes ->
                    if (isReverse)
                        notes.sortedByDescending(sortBy)
                    else
                        notes.sortedBy(sortBy)
                }.update(_uiState) { state, res ->
                    state.copy(fetchNotesState = res)
                }
            }
        }
    }

}