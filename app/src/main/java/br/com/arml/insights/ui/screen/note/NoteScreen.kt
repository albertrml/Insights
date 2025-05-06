package br.com.arml.insights.ui.screen.note

import android.annotation.SuppressLint
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.ui.component.common.HeaderScreen
import br.com.arml.insights.ui.component.common.InsightErrorSnackBar
import br.com.arml.insights.ui.component.note.NoteBodyContent
import br.com.arml.insights.ui.component.note.NoteDeleteAlert
import br.com.arml.insights.ui.component.note.NoteSheetContent
import br.com.arml.insights.ui.component.common.InsightFilterAndSort
import br.com.arml.insights.ui.screen.note.NoteEvent.OnClickToOpenDeleteDialog
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
    val tagScreenState = rememberNoteScreenState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            effect?.let { tagScreenState.onEffect(it) }
        }
    }

    BottomSheetScaffold(
        modifier = modifier.padding(top = 16.dp),
        scaffoldState = tagScreenState.bottomSheetState,
        sheetPeekHeight = tagScreenState.getSheetPeekHeight(),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        snackbarHost = {
            InsightErrorSnackBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                hostState = tagScreenState.bottomSheetState.snackbarHostState
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

            InsightFilterAndSort(
                modifier = modifier,
                sortedBy = { ascending ->
                    if (ascending)
                        viewModel.onEvent(NoteEvent.OnSortTitleByAscending)
                    else
                        viewModel.onEvent(NoteEvent.OnSortTitleByDescending)
                },
                searchQuery = tagScreenState.searchQuery,
                onSearchTextChange = {
                    tagScreenState.searchQuery = it
                    viewModel.onEvent(NoteEvent.OnSearch(it, SearchNoteCategory.ByTitle))
                },
                onRefreshTags = {
                    tagScreenState.searchQuery = ""
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
                showDialog = tagScreenState.isAlertDialogVisible,
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