package br.com.arml.insights.viewmodel.home

sealed class HomeUiEvent{
    data object OnFetchNotesByCreationDateInAscendingOrder: HomeUiEvent()
    data object OnFetchNotesByCreationDateInDescendingOrder: HomeUiEvent()
    data object OnFetchNotesBySituationsInAscendingOrder: HomeUiEvent()
    data object OnFetchNotesBySituationsInDescendingOrder: HomeUiEvent()
    data object OnFetchNotesByTagInAscendingOrder: HomeUiEvent()
    data object OnFetchNotesByTagInDescendingOrder: HomeUiEvent()
    data object OnFetchNotesByTitleInAscendingOrder: HomeUiEvent()
    data object OnFetchNotesByTitleInDescendingOrder: HomeUiEvent()
}