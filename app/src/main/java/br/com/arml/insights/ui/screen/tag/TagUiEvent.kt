package br.com.arml.insights.ui.screen.tag

import br.com.arml.insights.model.entity.TagUi

sealed class TagUiEvent {
    data class OnDeleteTagUi(val tagUi: TagUi) : TagUiEvent()
    data class OnEditTagUi(val tagUi: TagUi) : TagUiEvent()
    data class OnInsertTagUi(val newTagUi: TagUi) : TagUiEvent()
    data class OnSearchTagsUi(val query: String) : TagUiEvent()
    data object OnRetrieveTagUi : TagUiEvent()
}