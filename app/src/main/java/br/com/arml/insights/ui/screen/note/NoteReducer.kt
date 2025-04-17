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

        }
    }
}