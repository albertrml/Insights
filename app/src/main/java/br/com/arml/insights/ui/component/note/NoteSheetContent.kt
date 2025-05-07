package br.com.arml.insights.ui.component.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.ui.component.common.InsightHeaderForms
import br.com.arml.insights.ui.theme.dimens


@Composable
fun NoteSheetContent(
    modifier: Modifier = Modifier,
    selectedNote: NoteUi,
    onEditTitle: (String) -> Unit = {},
    onEditSituation: (String) -> Unit = {},
    onEditBody: (String) -> Unit = {},
    onClickClose: () -> Unit = {},
    onClickSave: (NoteUi) -> Unit = {}
){

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small),
    ) {

        InsightHeaderForms(
            modifier = Modifier.fillMaxWidth(),
            title = selectedNote.title,
            onClickClose = onClickClose
        )

        NoteForms(
            selectedNote = selectedNote,
            onEditTitle = onEditTitle,
            onEditSituation = onEditSituation,
            onEditBody = onEditBody,
            onClickSave = onClickSave,
        )

    }
}

@Preview(showBackground = true)
@Composable
fun NoteSheetContentPreview(){
    var note by remember { mutableStateOf(NoteUi.fromNote(null)) }
    NoteSheetContent(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small),
        selectedNote = note,
        onEditTitle = { note = note.copy(title = it) },
        onEditSituation = { note = note.copy(situation = it) },
        onEditBody = { note = note.copy(body = it) },
        onClickClose = {},
        onClickSave = {}
    )
}



