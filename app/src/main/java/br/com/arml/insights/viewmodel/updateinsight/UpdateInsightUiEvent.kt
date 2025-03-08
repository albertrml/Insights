package br.com.arml.insights.viewmodel.updateinsight

sealed class UpdateInsightUiEvent {
    data class OnFetchInsight(val id: Int) : UpdateInsightUiEvent()
    data class OnUpdateInsight(
        val id: Int,
        val title: String,
        val situation: String,
        val body: String,
        val creationDate: Long,
        val tagId: Int
    ) : UpdateInsightUiEvent()
    data object OnFetchTags: UpdateInsightUiEvent()
}