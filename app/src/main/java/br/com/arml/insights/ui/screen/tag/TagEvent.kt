package br.com.arml.insights.ui.screen.tag

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.ui.screen.common.Reducer

@Immutable
sealed class TagEvent : Reducer.ViewEvent {
    /* Actions to handle backend events */
    data class OnInsertOrUpdate(val operation: TagOperation): TagEvent()
    data class OnSearch(val query: String) : TagEvent()
    data object OnFetchAllItems : TagEvent()
    data object OnDelete : TagEvent()


    /* Actions to handle frontend events */
    data class OnEditName(val name: String) : TagEvent()
    data class OnEditDescription(val description: String) : TagEvent()
    data class OnEditColor(val color: Color) : TagEvent()
    data object OnClickToCloseSheet : TagEvent()
    data class OnClickToOpenSheet(
        val selectedTagUi: TagUi?,
        val tagOperation: TagOperation
    ) : TagEvent()
    data object OnSortTagsByNameAscending: TagEvent()
    data object OnSortTagsByNameDescending: TagEvent()
    data class OnClickToShowDeleteDialog(val selectedTag: TagUi) : TagEvent()
    data object OnClickToCloseDeleteDialog : TagEvent()
}

sealed class TagOperation{
    data object OnInsert : TagOperation()
    data object OnUpdate : TagOperation()
    data object None : TagOperation()
}