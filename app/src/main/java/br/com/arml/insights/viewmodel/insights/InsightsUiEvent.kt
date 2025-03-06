package br.com.arml.insights.viewmodel.insights

import br.com.arml.insights.model.entity.Note

sealed class InsightsUiEvent {
    data class OnDeleteNote(val note: Note) : InsightsUiEvent()
    data class OnFetchNotesByTitleInAscendingOrder(val idTag: Int) : InsightsUiEvent()
    data class OnFetchNotesBySituationInAscendingOrder(val idTag: Int) : InsightsUiEvent()
    data class OnFetchNotesByCreationDataInDescendingOrder(val idTag: Int) : InsightsUiEvent()
}