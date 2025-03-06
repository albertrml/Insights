package br.com.arml.insights.viewmodel.updatetag

import br.com.arml.insights.utils.data.Response

data class UpdateTagUiState(
    val updateTagState: Response<Unit> = Response.Loading,
)
