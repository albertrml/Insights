package br.com.arml.insights.ui.component.note

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.ui.component.common.InsightButton
import br.com.arml.insights.ui.component.common.InsightNotePad
import br.com.arml.insights.ui.component.common.InsightTextField
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
        modifier = modifier.scrollable(
            state = rememberScrollState(),
            orientation = Orientation.Vertical
        ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            IconButton(
                onClick = onClickClose
            ) {
                Icon(
                    modifier = Modifier.size(MaterialTheme.dimens.icon),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(
                        id = R.string.note_screen_close_menu,
                        selectedNote.title
                    )
                )
            }
        }


        Column (
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            InsightNotePad(
                header = {
                    InsightTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.dimens.small),
                        nameField = "Title",
                        text = selectedNote.title,
                        onChangeText = onEditTitle,
                        maxSize = 30,
                        textStyle = MaterialTheme.typography.bodyLarge
                    )

                    InsightTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.dimens.small),
                        nameField = "Situation",
                        text = selectedNote.situation,
                        onChangeText = onEditSituation,
                        maxSize = 30,
                        maxLines = 2,
                        textStyle = MaterialTheme.typography.bodyLarge
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.small),
                noteName = "What is your insight?",
                text = selectedNote.body,
                textStyle = MaterialTheme.typography.bodyLarge,
                onChangeText = onEditBody,
                maxSize = 1000,
                minLines = 20,
                maxLines = 20
            )

            InsightButton(
                text = stringResource(R.string.note_forms_save_button),
                onClick = { onClickSave(selectedNote) },
                iconRes = R.drawable.ic_save
            )

            Spacer(modifier = Modifier.size(MaterialTheme.dimens.small))

        }

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



