package br.com.arml.insights.viewmodel.insert

import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.utils.data.Response

data class InsertUiState (
    val tags: List<Tag> = emptyList(),
    val insertTagState: Response<Unit> = Response.Loading,
    val insertNoteState: Response<Unit> = Response.Loading
)