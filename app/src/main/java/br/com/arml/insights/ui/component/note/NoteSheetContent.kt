package br.com.arml.insights.ui.component.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.ui.component.common.InsightButton
import br.com.arml.insights.ui.component.common.InsightNotePad
import br.com.arml.insights.ui.component.common.InsightTextField


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
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            IconButton(
                modifier = Modifier,
                onClick = onClickClose
            ) {
                Icon(
                    modifier = modifier.size(50.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(
                        id = R.string.note_screen_close_menu,
                        selectedNote.title
                    )
                )
            }
        }

        InsightNotePad(
            header = {
                InsightTextField(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    nameField = "Title",
                    text = selectedNote.title,
                    onChangeText = onEditTitle,
                    maxSize = 30,
                )

                InsightTextField(
                    modifier = Modifier.fillMaxWidth(),
                    nameField = "Situation",
                    text = selectedNote.situation,
                    onChangeText = onEditSituation,
                    textStyle = MaterialTheme.typography.headlineSmall,
                    maxSize = 30,
                    maxLines = 2,
                )
            },
            modifier = Modifier.fillMaxWidth(),
            noteName = "What is your insight?",
            text = selectedNote.body,
            onChangeText = onEditBody,
            maxSize = 1000,
            minLines = 20,
            maxLines = 20
        )

        InsightButton(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp),
            text = stringResource(R.string.note_forms_save_button),
            onClick = { onClickSave(selectedNote) },
            iconRes = R.drawable.ic_save
        )

    }
}

@Preview(showBackground = true)
@Composable
fun NoteSheetContentPreview(){
    var note by remember { mutableStateOf(NoteUi.fromNote(null)) }
    NoteSheetContent(
        selectedNote = note,
        onEditTitle = { note = note.copy(title = it) },
        onEditSituation = { note = note.copy(situation = it) },
        onEditBody = { note = note.copy(body = it) },
        onClickClose = {},
        onClickSave = {}
    )
}



