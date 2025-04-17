package br.com.arml.insights.ui.screen.note

import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.ui.screen.common.Reducer


sealed class NoteEvent: Reducer.ViewEvent{
    /* Actions to handle backend events */
    data class OnInsertOrUpdate(val operation: NoteOperation): NoteEvent()
    data object OnFetchAllNotes: NoteEvent()
    data object OnDeleteNote : NoteEvent()

    /* Actions to handle frontend events */
    data class OnClickToOpenSheet(
        val selectedNote: NoteUi,
        val noteOperation: NoteOperation
    ): NoteEvent()
    data object OnClickToCloseSheet: NoteEvent()
    data class OnClickToOpenDeleteDialog(val selectedNote: NoteUi): NoteEvent()
    data object OnClickToCloseDeleteDialog: NoteEvent()
    data class OnEditTitle(val title: String): NoteEvent()
    data class OnEditSituation(val situation: String): NoteEvent()
    data class OnEditBody(val body: String): NoteEvent()

}

sealed class NoteOperation{
    data object OnInsert: NoteOperation()
    data object OnUpdate: NoteOperation()
    data object None: NoteOperation()
}