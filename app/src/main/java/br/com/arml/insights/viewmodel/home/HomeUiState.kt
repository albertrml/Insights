package br.com.arml.insights.viewmodel.home

import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.utils.data.Response

data class HomeUiState (
    val deleteNoteState: Response<Unit> = Response.Loading,
    val fetchNotesState: Response<List<Note>> = Response.Loading
)