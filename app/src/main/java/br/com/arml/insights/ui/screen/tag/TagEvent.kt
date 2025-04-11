package br.com.arml.insights.ui.screen.tag

import br.com.arml.insights.model.entity.TagUi

sealed class TagEvent {
    data class OnDeleteTag(val tagUi: TagUi) : TagEvent()
    data class OnEditTag(val tagUi: TagUi) : TagEvent()
    data class OnInsertTag(val newTagUi: TagUi) : TagEvent()
    data class OnSearchTags(val query: String) : TagEvent()
    data object OnRetrieveTag : TagEvent()
}