package br.com.arml.insights.viewmodel.newinsight

sealed class NewInsightUiEvent {
    data class OnInsertInsight(
        val title: String,
        val body: String,
        val situation: String,
        val tagId: Int,
    ): NewInsightUiEvent()
    data object OnFetchTags: NewInsightUiEvent()
}