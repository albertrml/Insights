package br.com.arml.insights.viewmodel.home

import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.utils.data.Response

data class HomeUiState (
    val deleteTagState: Response<Unit> = Response.Loading,
    val fetchTagsState: Response<List<Tag>> = Response.Loading
)