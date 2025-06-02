package br.com.arml.insights.ui.screen.note

import androidx.lifecycle.viewModelScope
import br.com.arml.insights.domain.NoteUiUseCase
import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.ui.screen.common.BaseViewModel
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.data.SearchNoteCategory
import br.com.arml.insights.utils.data.SortedNote
import br.com.arml.insights.utils.data.SortedTag
import br.com.arml.insights.utils.data.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUiUseCase: NoteUiUseCase,
    reducer: NoteReducer,
) : BaseViewModel<NoteState, NoteEvent, NoteEffect>(
    initialState = NoteState(),
    reducer = reducer,
){
    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.OnDeleteNote -> { deleteNote() }
            is NoteEvent.OnInsertOrUpdate -> {
                when(event.operation){
                    is NoteOperation.OnInsert -> insertNote()
                    is NoteOperation.OnUpdate -> updateNote()
                    is NoteOperation.None -> {}
                }
            }
            is NoteEvent.OnFetchAllNotes -> { fetchAllNotes(SortedNote.ByTitleAscending) }
            is NoteEvent.OnSearch -> { searchNoteByTitle(event.query, event.searchNoteCategory) }
            is NoteEvent.OnFetchTags -> { fetchTags() }
            is NoteEvent.OnInit -> { initViewModel(event.tagId) }
            else -> sendEventForEffect(event)
        }
    }

    private fun initViewModel(tagId: Int){
        _state.update { state -> state.copy(tagId = tagId) }
        fetchAllNotes(SortedNote.ByTitleAscending)
        fetchTags()
    }

    private fun fetchTags(sortBy: SortedTag = SortedTag.ByNameAscending){
        viewModelScope.launch {
            noteUiUseCase.fetchTagUi(sortBy = sortBy).collectLatest { response ->
                if(response is Response.Failure)
                    sendEffect(
                        NoteEffect.ShowSnackBar(
                            response.exception.message?:"Something went wrong"
                        )
                    )
                response.update(_state) { state, res ->
                    state.copy(tags = res)
                }
            }
        }
    }

    private fun deleteNote(){
        viewModelScope.launch {
            val noteForDelete = state.value.selectedNote
            noteUiUseCase.deleteNote(noteForDelete).collect { response ->
                response.update(_state) { state, res ->
                    when(res){
                        is Response.Loading -> {
                            state.copy(operationState = res)
                        }
                        is Response.Success -> {
                            sendEffect(NoteEffect.OnHideDeleteDialog)
                            state.copy(
                                selectedNote = resetNoteUi(),
                                operationState = res
                            )
                        }
                        is Response.Failure -> {
                            val failureMsg = res.exception.message?:"Something went wrong"
                            sendEffect(NoteEffect.ShowSnackBar(failureMsg))
                            state.copy(operationState = res)
                        }
                    }
                }
            }
        }
    }

    private fun insertNote(){
        viewModelScope.launch {
            val newNote = state.value.selectedNote.copy(
                creationDate = Date()
            )

            val (isNoteUiValid, invalidException) = NoteUi.isValid(newNote)
            if(!isNoteUiValid){
                sendEffect(NoteEffect.ShowSnackBar(invalidException!!.message))
                _state.update { state ->
                    state.copy(
                        operationState = Response.Failure(invalidException)
                    )
                }
                return@launch
            }

            noteUiUseCase.addNote(newNote).collect { response ->
                response.update(_state) { state, res ->
                    when(res){
                        is Response.Success -> {
                            sendEffect(NoteEffect.OnHideContentSheet)
                            state.copy(
                                selectedNote = resetNoteUi(),
                                noteOperation = NoteOperation.None,
                                operationState = res
                            )
                        }
                        is Response.Loading -> { state.copy(operationState = res) }
                        is Response.Failure -> {
                            val failureMsg = res.exception.message?:"Something went wrong"
                            sendEffect(NoteEffect.ShowSnackBar(failureMsg))
                            state.copy(operationState = res)
                        }
                    }
                }
            }
        }
    }

    private fun searchNoteByTitle(query: String, searchNoteCategory: SearchNoteCategory) {
        viewModelScope.launch {
            noteUiUseCase.searchNotes(state.value.tagId, query, searchNoteCategory).collect {
                it.update(_state) { state, res ->
                    state.copy(notes = res)
                }
            }
        }
    }

    private fun updateNote(){
        viewModelScope.launch{
            val updatedNote = state.value.selectedNote

            val (isNoteUiValid, invalidException) = NoteUi.isValid(updatedNote)
            if (!isNoteUiValid) {
                sendEffect(NoteEffect.ShowSnackBar(invalidException!!.message))
                _state.update { state ->
                    state.copy(
                        operationState = Response.Failure(invalidException)
                    )
                }
                return@launch
            }

            noteUiUseCase.updateNote(updatedNote).collect { response ->
                response.update(_state) { state, res ->
                    when (res) {
                        is Response.Success -> {
                            sendEffect(NoteEffect.OnHideContentSheet)
                            state.copy(
                                selectedNote = resetNoteUi(),
                                noteOperation = NoteOperation.None,
                                operationState = res
                            )
                        }

                        is Response.Loading -> {
                            state.copy(operationState = res)
                        }

                        is Response.Failure -> {
                            val failureMsg = res.exception.message ?: "Something went wrong"
                            sendEffect(NoteEffect.ShowSnackBar(failureMsg))
                            state.copy(operationState = res)
                        }
                    }
                }
            }
        }
    }

    private fun fetchAllNotes(sortedNote: SortedNote) {
        viewModelScope.launch {
            noteUiUseCase.fetchNotesByTag(state.value.tagId,sortedNote).collectLatest { response ->
                if(response is Response.Failure)
                    sendEffect(
                        NoteEffect.ShowSnackBar(
                            response.exception.message?:"Something went wrong"
                        )
                    )
                response.update(_state) { state, res ->
                    state.copy(notes = res)
                }
            }
        }
    }

    private fun resetNoteUi() = NoteUi.fromNote(null).copy(tagId = state.value.tagId)
}