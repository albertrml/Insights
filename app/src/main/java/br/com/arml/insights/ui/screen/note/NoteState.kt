package br.com.arml.insights.ui.screen.note

import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.ui.screen.common.Reducer
import br.com.arml.insights.utils.data.Response

data class NoteState (
    val notes: Response<List<NoteUi>> = Response.Loading,
    val tags: Response<List<TagUi>> = Response.Loading,
    val operationState: Response<Unit> = Response.Loading,
    val noteOperation: NoteOperation = NoteOperation.None,
    val selectedNote: NoteUi = NoteUi.fromNote(null),
    val tagId: Int = 0
) : Reducer.ViewState