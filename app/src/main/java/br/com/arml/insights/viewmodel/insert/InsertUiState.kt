package br.com.arml.insights.viewmodel.insert

import br.com.arml.insights.utils.data.Response

data class InsertUiState (
    val insertTagState: Response<Unit> = Response.Loading,
    val insertNoteState: Response<Unit> = Response.Loading
)