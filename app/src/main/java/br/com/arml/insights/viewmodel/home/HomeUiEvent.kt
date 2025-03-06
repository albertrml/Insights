package br.com.arml.insights.viewmodel.home

import br.com.arml.insights.model.entity.Tag

sealed class HomeUiEvent{
    data class OnDeleteTag(val tag: Tag): HomeUiEvent()
    data object OnFetchTagsByColorInAscendingOrder: HomeUiEvent()
    data object OnFetchTagsByNameInAscendingOrder: HomeUiEvent()
}