package br.com.arml.insights.viewmodel.insert

sealed class InsertUiEvent {
    data class OnInsertTag(
        val name: String,
        val color: String,
        val description: String
    ) : InsertUiEvent()

    data class OnInsertInsight(
        val title: String,
        val body: String,
        val situation: String,
        val tagName: String
    ) : InsertUiEvent()
}