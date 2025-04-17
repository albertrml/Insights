package br.com.arml.insights.ui.screen.tag

import androidx.compose.runtime.Immutable
import br.com.arml.insights.ui.screen.common.Reducer

@Immutable
sealed class TagEffect : Reducer.ViewEffect {
    data object OnShowContentSheet: TagEffect()
    data object OnHideBottomSheet: TagEffect()
    data object OnShowDeleteDialog: TagEffect()
    data object OnHideDeleteDialog: TagEffect()
    data class ShowSnackBar(val message: String) : TagEffect()
}