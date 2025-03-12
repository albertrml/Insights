package br.com.arml.insights.viewmodel.insight

import br.com.arml.insights.model.entity.Note

sealed class InsightUiEvent {
    data class OnDeleteNote(val note: Note) : InsightUiEvent()
    data class OnFetchNotesByTitleInAscendingOrder(val idTag: Int) : InsightUiEvent()
    data class OnFetchNotesBySituationInAscendingOrder(val idTag: Int) : InsightUiEvent()
    data class OnFetchNotesByCreationDataInDescendingOrder(val idTag: Int) : InsightUiEvent()
}