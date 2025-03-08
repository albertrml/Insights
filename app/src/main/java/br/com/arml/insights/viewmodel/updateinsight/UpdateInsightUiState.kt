package br.com.arml.insights.viewmodel.updateinsight

import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.utils.data.Response

data class UpdateInsightUiState(
    val fetchInsightState: Response<Note> = Response.Loading,
    val fetchTagsState: Response<List<Tag>> = Response.Loading,
    val updateInsightState: Response<Unit> = Response.Loading
)