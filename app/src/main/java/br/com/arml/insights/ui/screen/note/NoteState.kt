package br.com.arml.insights.ui.screen.note

import br.com.arml.core.response.Response
import br.com.arml.insights.model.domain.NoteUi
import br.com.arml.insights.model.domain.TagUi
import br.com.arml.insights.ui.screen.common.Reducer

data class NoteState (
    val notes: Response<List<NoteUi>> = Response.Loading,
    val tags: Response<List<TagUi>> = Response.Loading,
    val operationState: Response<Unit> = Response.Loading,
    val noteOperation: NoteOperation = NoteOperation.None,
    val selectedNote: NoteUi = NoteUi.fromNoteEntity(null),
    val tagId: Long = 0
) : Reducer.ViewState