package br.com.arml.insights.ui.screen.tag

import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.utils.data.Response

data class TagState(
    val deleteState: Response<Unit> = Response.Loading,
    val editState: Response<Unit> = Response.Loading,
    val insertState: Response<Unit> = Response.Loading,
    val retrieveState: Response<List<TagUi>> = Response.Loading,
    val searchState: List<TagUi> = emptyList<TagUi>()
)