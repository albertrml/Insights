package br.com.arml.insights.ui.screen.note

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.ui.component.common.HeaderScreen
import br.com.arml.insights.ui.component.common.InsightErrorSnackBar
import br.com.arml.insights.ui.component.note.NoteBodyContent
import br.com.arml.insights.ui.component.note.NoteSheetContent
import br.com.arml.insights.ui.component.tag.TagFilterAndSort

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    tagId: Int,
    tagName: String,
    onNavigateTo: () -> Unit = {},
){
    val viewModel = hiltViewModel<NoteViewModel>()
    val noteState by viewModel.state.collectAsStateWithLifecycle()

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

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect){
                is NoteEffect.OnShowContentSheet -> {
                    isVisibleContentSheet = true
                }
                is NoteEffect.OnHideContentSheet -> {
                    isVisibleContentSheet = false
                }
                is NoteEffect.ShowSnackBar -> {
                    bottomSheetState.snackbarHostState.showSnackbar(effect.message)
                }
                else -> {}
            }
        }
    }

    BottomSheetScaffold(
        modifier = modifier.padding(top = 16.dp),
        scaffoldState = bottomSheetState,
        sheetPeekHeight = animatedPeekHeight.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        snackbarHost = {
            InsightErrorSnackBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 32.dp),
                hostState = bottomSheetState.snackbarHostState
            )
        },
        sheetContent = {
            NoteSheetContent(
                modifier = modifier.padding(horizontal = 8.dp),
                selectedNote = noteState.selectedNote,
                onEditTitle = { viewModel.onEvent(NoteEvent.OnEditTitle(it)) },
                onEditSituation = { viewModel.onEvent(NoteEvent.OnEditSituation(it)) },
                onEditBody = { viewModel.onEvent(NoteEvent.OnEditBody(it)) },
                onClickClose = {
                    viewModel.onEvent(NoteEvent.OnClickToCloseSheet)
                },
                onClickSave = {
                    viewModel.onEvent(NoteEvent.OnInsertOrUpdate(noteState.noteOperation))
                }
            )
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
                    viewModel.onEvent(
                        NoteEvent.OnClickToOpenSheet(
                            selectedNote = NoteUi.fromNote(null).copy(tagId = tagId),
                            noteOperation = NoteOperation.OnInsert
                        )
                    )
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
                    viewModel.onEvent(NoteEvent.OnFetchAllNotes)
                }
            )

            NoteBodyContent(
                modifier = modifier.fillMaxWidth(),
                response = noteState.notes,
                onEditNote = {
                    viewModel.onEvent(
                        NoteEvent.OnClickToOpenSheet(
                            selectedNote = it,
                            noteOperation = NoteOperation.OnUpdate
                        )
                    )
                },
                onDeleteNote = {
                    /*viewModel.onEvent(NoteEvent.OnDeleteNote(it))*/
                }
            )

        }
    }
}