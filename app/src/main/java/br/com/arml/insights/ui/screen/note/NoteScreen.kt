package br.com.arml.insights.ui.screen.note

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import br.com.arml.insights.ui.component.common.InsightErrorSnackBar
import br.com.arml.insights.ui.component.common.InsightFilterAndSort
import br.com.arml.insights.ui.component.common.InsightHeaderScreen
import br.com.arml.insights.ui.component.note.NoteBodyContent
import br.com.arml.insights.ui.component.note.NoteDeleteAlert
import br.com.arml.insights.ui.component.note.NoteSheetContent
import br.com.arml.insights.ui.screen.common.setMargin
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

    LaunchedEffect(tagId) {
        viewModel.onEvent(NoteEvent.OnInit(tagId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            effect?.let { noteScreenState.onEffect(it) }
        }
    }

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = noteScreenState.bottomSheetState,
        sheetSwipeEnabled = false,
        sheetPeekHeight = noteScreenState.rememberNoteSheetContent(),
        sheetShape = RoundedCornerShape(
            topStart = MaterialTheme.dimens.largeCornerRadius,
            topEnd = MaterialTheme.dimens.largeCornerRadius
        ),
        snackbarHost = {
            InsightErrorSnackBar(
                modifier = Modifier,
                hostState = noteScreenState.bottomSheetState.snackbarHostState
            )
        },
        sheetContent = {
            NoteSheetContent(
                modifier = Modifier.setMargin(),
                selectedNote = noteState.selectedNote,
                selectedOperation = noteState.noteOperation,
                tags = noteState.tags,
                onEditTitle = { viewModel.onEvent(NoteEvent.OnEditTitle(it)) },
                onEditSituation = { viewModel.onEvent(NoteEvent.OnEditSituation(it)) },
                onEditBody = { viewModel.onEvent(NoteEvent.OnEditBody(it)) },
                onEditTagId = { viewModel.onEvent(NoteEvent.OnSelectNewTag(it)) },
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
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.mediumSpacing)
        ) {
            InsightHeaderScreen(
                modifier = Modifier,
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
                modifier = Modifier,
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
                modifier = Modifier,
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
                modifier = Modifier,
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