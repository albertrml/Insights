package br.com.arml.insights.ui.screen.note

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.arml.insights.domain.NoteUiUseCase
import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.ui.screen.common.BaseViewModel
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.data.SearchNoteCategory
import br.com.arml.insights.utils.data.SortedNote
import br.com.arml.insights.utils.data.update
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUiUseCase: NoteUiUseCase,
    reducer: NoteReducer,
    saveStateHandle: SavedStateHandle,
) : BaseViewModel<NoteState, NoteEvent, NoteEffect>(
    initialState = NoteState(),
    reducer = reducer,
){
    private val tagId = saveStateHandle.get<Int>("tag_id")?:0


    init {
        Log.i("NoteViewModel", "tagId: $tagId")
        fetchAllNotes(tagId, SortedNote.ByTitleAscending)
    }

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
            is NoteEvent.OnFetchAllNotes -> { fetchAllNotes(tagId, SortedNote.ByTitleAscending) }
            is NoteEvent.OnSearch -> { searchNoteByTitle(event.query, event.searchNoteCategory) }
            else -> sendEventForEffect(event)
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
            noteUiUseCase.searchNotes(query, searchNoteCategory).collect {
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

    private fun fetchAllNotes(tagId: Int, sortedNote: SortedNote) {
        viewModelScope.launch {
            noteUiUseCase.fetchNotesByTag(tagId,sortedNote).collectLatest { response ->
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

    private fun resetNoteUi() = NoteUi.fromNote(null).copy(tagId = tagId)
}