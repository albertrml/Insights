package br.com.arml.insights.ui.screen.note

import br.com.arml.insights.ui.screen.common.Reducer

sealed class NoteEffect : Reducer.ViewEffect {
    data object OnShowContentSheet: NoteEffect()
    data object OnHideContentSheet: NoteEffect()
    data class ShowSnackBar(val message: String) : NoteEffect()
}