package br.com.arml.insights.viewmodel.newinsight

import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.utils.data.Response

data class NewInsightUiState(
    val insertInsightState: Response<Unit> = Response.Loading,
    val fetchTags: Response<List<Tag>> = Response.Loading
)
