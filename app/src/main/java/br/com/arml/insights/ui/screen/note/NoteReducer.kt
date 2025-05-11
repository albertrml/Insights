package br.com.arml.insights.ui.screen.note

import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.ui.screen.common.Reducer
import br.com.arml.insights.utils.data.Response
import javax.inject.Inject

class NoteReducer @Inject constructor() : Reducer<NoteState, NoteEvent, NoteEffect> {
    override fun reduce(
        previousState: NoteState,
        event: NoteEvent
    ): Pair<NoteState, NoteEffect?> {
        return when(event){
            is NoteEvent.OnInsertOrUpdate -> previousState to null
            is NoteEvent.OnFetchAllNotes -> previousState to null
            is NoteEvent.OnDeleteNote -> previousState to null
            is NoteEvent.OnSearch -> previousState to null
            is NoteEvent.OnFetchTags -> previousState to null
            is NoteEvent.OnSelectTag -> {
                val newNote = previousState.selectedNote.copy(tagId = event.tagId)
                val updatedState = previousState.copy(selectedNote = newNote)
                updatedState to null
            }

            is NoteEvent.OnEditTitle -> {
                val updatedState = previousState.selectedNote.copy(title = event.title)
                previousState.copy(selectedNote = updatedState) to null
            }
            is NoteEvent.OnEditSituation -> {
                val updatedState = previousState.selectedNote.copy(situation = event.situation)
                previousState.copy(selectedNote = updatedState) to null
            }
            is NoteEvent.OnEditBody -> {
                val updatedState = previousState.selectedNote.copy(body = event.body)
                previousState.copy(selectedNote = updatedState) to null
            }

            is NoteEvent.OnSortTitleByAscending -> {
                val newState =  if (previousState.notes is Response.Success){
                    previousState.copy(
                        notes = Response.Success(
                            previousState.notes.result.sortedBy{ it.title }
                        )
                    )
                } else { previousState }
                newState to null
            }
            is NoteEvent.OnSortTitleByDescending -> {
                val newState =  if (previousState.notes is Response.Success){
                    previousState.copy(
                        notes = Response.Success(
                            previousState.notes.result.sortedByDescending{ it.title }
                        )
                    )
                } else { previousState }
                newState to null
            }


            is NoteEvent.OnClickToOpenSheet -> {
                previousState.copy(
                    selectedNote = event.selectedNote,
                    noteOperation = event.noteOperation,
                    operationState = Response.Loading
                ) to NoteEffect.OnShowContentSheet
            }
            is NoteEvent.OnClickToCloseSheet -> {
                previousState.copy(
                    selectedNote = NoteUi.fromNote(null),
                    noteOperation = NoteOperation.None
                ) to NoteEffect.OnHideContentSheet
            }
            is NoteEvent.OnClickToOpenDeleteDialog -> {
                previousState.copy(
                    selectedNote = event.selectedNote
                ) to NoteEffect.OnShowDeleteDialog
            }
            is NoteEvent.OnClickToCloseDeleteDialog -> {
                previousState.copy(
                    selectedNote = NoteUi.fromNote(null)
                ) to NoteEffect.OnHideDeleteDialog
            }

        }
    }
}