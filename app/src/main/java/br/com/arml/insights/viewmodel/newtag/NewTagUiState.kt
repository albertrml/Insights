package br.com.arml.insights.viewmodel.newtag

import br.com.arml.insights.utils.data.Response

data class NewTagUiState (
    val insertTagState: Response<Unit> = Response.Loading
)