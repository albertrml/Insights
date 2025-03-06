package br.com.arml.insights.viewmodel.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.repository.NoteRepository
import br.com.arml.insights.utils.data.mapTo
import br.com.arml.insights.utils.data.update
import br.com.arml.insights.viewmodel.insights.InsightsUiEvent.OnDeleteNote
import br.com.arml.insights.viewmodel.insights.InsightsUiEvent.OnFetchNotesByCreationDataInDescendingOrder
import br.com.arml.insights.viewmodel.insights.InsightsUiEvent.OnFetchNotesByTitleInAscendingOrder
import br.com.arml.insights.viewmodel.insights.InsightsUiEvent.OnFetchNotesBySituationInAscendingOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsightsViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(InsightsUiState())
    val uiState: StateFlow<InsightsUiState> = _uiState.asStateFlow()

    fun onEvent(event: InsightsUiEvent){
        when(event) {
            is OnDeleteNote -> {}
            is OnFetchNotesByCreationDataInDescendingOrder -> {
                fetchNotes(event.idTag) { it.creationDate }
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
        crossinline sortBy: (Note) -> T
    ) {
        viewModelScope.launch {
            noteRepository.getByTag(idTag).collect { response ->
                response.mapTo { notes ->
                    notes.sortedBy(sortBy)
                }.update(_uiState) { state, res ->
                    state.copy(fetchNotesState = res)
                }
            }
        }
    }

}