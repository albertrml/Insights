package br.com.arml.insights.ui.screen.note

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.ui.component.common.InsightHeaderScreen
import br.com.arml.insights.ui.component.common.InsightErrorSnackBar
import br.com.arml.insights.ui.component.note.NoteBodyContent
import br.com.arml.insights.ui.component.note.NoteDeleteAlert
import br.com.arml.insights.ui.component.note.NoteSheetContent
import br.com.arml.insights.ui.component.common.InsightFilterAndSort
import br.com.arml.insights.ui.screen.note.NoteEvent.OnClickToOpenDeleteDialog
import br.com.arml.insights.ui.theme.dimens
import br.com.arml.insights.utils.data.SearchNoteCategory

@SuppressLint("ConfigurationScreenWidthHeight")
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
    val noteScreenState = rememberNoteScreenState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            effect?.let { noteScreenState.onEffect(it) }
        }
    }

    BottomSheetScaffold(
        modifier = modifier
            .consumeWindowInsets(WindowInsets.safeDrawing)
            .fillMaxSize(),
        scaffoldState = noteScreenState.bottomSheetState,
        sheetSwipeEnabled = false,
        sheetPeekHeight = noteScreenState.rememberNoteSheetContent(),
        sheetShape = RoundedCornerShape(
            topStart = MaterialTheme.dimens.medium,
            topEnd = MaterialTheme.dimens.medium
        ),
        snackbarHost = {
            InsightErrorSnackBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.medium)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                hostState = noteScreenState.bottomSheetState.snackbarHostState
            )
        },
        sheetContent = {
            NoteSheetContent(
                modifier = modifier
                    .padding(horizontal = MaterialTheme.dimens.medium)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                selectedNote = noteState.selectedNote,
                selectedOperation = noteState.noteOperation,
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
                .padding(MaterialTheme.dimens.medium)
                .consumeWindowInsets(padding),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium)
        ) {
            InsightHeaderScreen(
                modifier = Modifier
                    .padding(top = MaterialTheme.dimens.large),
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

            InsightFilterAndSort(
                modifier = modifier,
                sortedBy = { ascending ->
                    if (ascending)
                        viewModel.onEvent(NoteEvent.OnSortTitleByAscending)
                    else
                        viewModel.onEvent(NoteEvent.OnSortTitleByDescending)
                },
                searchQuery = noteScreenState.searchQuery,
                onSearchTextChange = {
                    noteScreenState.searchQuery = it
                    viewModel.onEvent(NoteEvent.OnSearch(it, SearchNoteCategory.ByTitle))
                },
                onRefreshTags = {
                    noteScreenState.searchQuery = ""
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
                onDeleteNote = { noteUi ->
                    viewModel.onEvent(OnClickToOpenDeleteDialog(noteUi))
                }
            )

            NoteDeleteAlert(
                modifier = modifier,
                note = noteState.selectedNote,
                showDialog = noteScreenState.isAlertDialogVisible,
                onDismissRequest = {
                    viewModel.onEvent(NoteEvent.OnClickToCloseDeleteDialog)
                },
                onConfirmation = {
                    viewModel.onEvent(NoteEvent.OnDeleteNote)
                }
            )

        }
    }
}