package br.com.arml.insights.ui.screen.note

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import br.com.arml.insights.R
import br.com.arml.insights.ui.component.common.HeaderScreen
import br.com.arml.insights.ui.component.tag.TagFilterAndSort

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    tagId: Int,
    tagName: String,
    onNavigateTo: () -> Unit = {},
){

    var isVisibleContentSheet by rememberSaveable { mutableStateOf(false) }
    var isAlertDialogVisible by rememberSaveable { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("")}

    val bottomSheetState = rememberBottomSheetScaffoldState()
    val configuration = LocalConfiguration.current
    val targetPeekHeight = if(isVisibleContentSheet) configuration.screenHeightDp.dp * 1f else 0.dp
    val animatedPeekHeight by animateFloatAsState(
        targetValue = targetPeekHeight.value,
        animationSpec = tween(durationMillis = 500),
    )

    BottomSheetScaffold(
        modifier = modifier.padding(top = 16.dp),
        scaffoldState = bottomSheetState,
        sheetPeekHeight = animatedPeekHeight.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        snackbarHost = {

        },
        sheetContent = {

        }
    ) { padding ->
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HeaderScreen(
                modifier = Modifier
                    .padding(top = 24.dp),
                iconResId = R.drawable.ic_note,
                title = tagName,
                onAddItem = {

                }
            )

            TagFilterAndSort(
                modifier = modifier,
                sortedBy = { ascending -> },
                searchQuery = searchQuery,
                onSearchTextChange = {
                    searchQuery = it
                },
                onRefreshTags = {
                    searchQuery = ""
                }
            )

        }
    }
}