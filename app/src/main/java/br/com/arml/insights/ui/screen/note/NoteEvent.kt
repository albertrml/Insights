package br.com.arml.insights.ui.screen.note

import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.ui.screen.common.Reducer
import br.com.arml.insights.utils.data.SearchNoteCategory


sealed class NoteEvent: Reducer.ViewEvent{
    /* Actions to handle backend events */
    data class OnInit(val tagId: Int) : NoteEvent()
    data object OnDeleteNote : NoteEvent()
    data object OnFetchAllNotes: NoteEvent()
    data class OnInsertOrUpdate(val operation: NoteOperation): NoteEvent()
    data class OnSearch(
        val query: String,
        val searchNoteCategory: SearchNoteCategory
    ): NoteEvent()
    data object OnFetchTags: NoteEvent()
    data class OnSelectNewTag(val tagId: Int): NoteEvent()

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

    data object OnSortTitleByAscending: NoteEvent()
    data object OnSortTitleByDescending: NoteEvent()
}

sealed class NoteOperation{
    data object OnInsert: NoteOperation()
    data object OnUpdate: NoteOperation()
    data object None: NoteOperation()
}