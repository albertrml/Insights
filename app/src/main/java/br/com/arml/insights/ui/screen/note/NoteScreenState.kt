package br.com.arml.insights.ui.screen.note

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import br.com.arml.insights.ui.screen.common.rememberAnimatedSheetPeekHeight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NoteScreenState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val bottomSheetState: BottomSheetScaffoldState,
    val scope: CoroutineScope,
){
    var isVisibleContentSheet by mutableStateOf(false)
    var isAlertDialogVisible by mutableStateOf(false)
    var searchQuery by mutableStateOf("")

    fun onEffect(effect: NoteEffect) {
        when(effect){
            is NoteEffect.OnShowContentSheet -> isVisibleContentSheet = true
            is NoteEffect.OnHideContentSheet -> isVisibleContentSheet = false
            is NoteEffect.OnShowDeleteDialog -> isAlertDialogVisible = true
            is NoteEffect.OnHideDeleteDialog -> isAlertDialogVisible = false
            is NoteEffect.ShowSnackBar -> showSnackBar(effect.message)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun showSnackBar(message: String){
        scope.launch {
            bottomSheetState.snackbarHostState.showSnackbar(message)
        }
    }

    @Composable
    fun rememberNoteSheetContent () = rememberAnimatedSheetPeekHeight(isVisibleContentSheet)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberNoteScreenState(
    bottomSheetState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    scope: CoroutineScope = rememberCoroutineScope(),
) = remember(bottomSheetState) {
    NoteScreenState(bottomSheetState,scope)
}