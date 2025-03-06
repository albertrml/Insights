package br.com.arml.insights.viewmodel.newtag

sealed class NewTagUiEvent {
    data class OnNewTag(
        val name: String,
        val color: String,
        val description: String
    ) : NewTagUiEvent()
}