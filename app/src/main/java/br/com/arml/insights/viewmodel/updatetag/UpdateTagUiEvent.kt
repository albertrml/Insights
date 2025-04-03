package br.com.arml.insights.viewmodel.updatetag

sealed class UpdateTagUiEvent {
    data class OnUpdateTag(
        val tagId: Int,
        val newName: String,
        val newDescription: String,
        val newColor: Long
    ): UpdateTagUiEvent()
}