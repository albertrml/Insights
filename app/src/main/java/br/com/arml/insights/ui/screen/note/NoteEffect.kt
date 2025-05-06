package br.com.arml.insights.ui.screen.note

import androidx.compose.runtime.Immutable
import br.com.arml.insights.ui.screen.common.Reducer

@Immutable
sealed class NoteEffect : Reducer.ViewEffect {
    data object OnShowContentSheet: NoteEffect()
    data object OnHideContentSheet: NoteEffect()
    data object OnShowDeleteDialog: NoteEffect()
    data object OnHideDeleteDialog: NoteEffect()
    data class ShowSnackBar(val message: String) : NoteEffect()
}