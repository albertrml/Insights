package br.com.arml.insights.ui.screen.tag

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TagScreenState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val bottomSheetState: BottomSheetScaffoldState,
    val scope: CoroutineScope,
){
    var isVisibleContentSheet by mutableStateOf(false)
    var isAlertDialogVisible by mutableStateOf(false)
    var searchQuery by mutableStateOf("")

    fun onEffect(effect: TagEffect) {
        when (effect) {
            is TagEffect.OnShowContentSheet -> isVisibleContentSheet = true
            is TagEffect.OnHideBottomSheet -> isVisibleContentSheet = false
            is TagEffect.OnShowDeleteDialog -> isAlertDialogVisible = true
            is TagEffect.OnHideDeleteDialog -> isAlertDialogVisible = false
            is TagEffect.ShowSnackBar -> showSnackBar(effect.message)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun showSnackBar(message: String){
        scope.launch {
            bottomSheetState.snackbarHostState.showSnackbar(message)
        }
    }

    @Composable
    fun getSheetPeekHeight() = animateFloatAsState(
        targetValue = if(isVisibleContentSheet) {
            (LocalConfiguration.current.screenHeightDp.dp * 1.0f).value
        } else { 0.0f },
        animationSpec = tween(durationMillis = 500),
    ).value.dp

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberTagScreenState(
    bottomSheetState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    scope: CoroutineScope = rememberCoroutineScope(),
) = remember(bottomSheetState) {
    TagScreenState(bottomSheetState,scope)
}