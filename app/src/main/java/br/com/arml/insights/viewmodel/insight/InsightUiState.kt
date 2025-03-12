package br.com.arml.insights.viewmodel.insight

import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.utils.data.Response

data class InsightUiState(
    val deleteNoteState: Response<Unit> = Response.Loading,
    val fetchNotesState: Response<List<Note>> = Response.Loading
)