package br.com.arml.insights.ui.component.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.ui.screen.note.NoteOperation
import br.com.arml.insights.ui.theme.Green500
import br.com.arml.insights.ui.theme.RedBase
import br.com.arml.insights.ui.theme.dimens


@Composable
fun NoteSheetContent(
    modifier: Modifier = Modifier,
    selectedNote: NoteUi,
    selectedOperation: NoteOperation,
    onEditTitle: (String) -> Unit = {},
    onEditSituation: (String) -> Unit = {},
    onEditBody: (String) -> Unit = {},
    onClickClose: () -> Unit = {},
    onClickSave: (NoteUi) -> Unit = {}
){

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium),
    ) {
        NoteSheetHeader(
            modifier = Modifier.fillMaxWidth(),
            operation = selectedOperation,
            onClickClose = onClickClose,
            onClickSave = { onClickSave(selectedNote) }
        )

        HorizontalDivider(
            modifier = Modifier,
            thickness = MaterialTheme.dimens.smallThickness
        )

        NoteForms(
            modifier = Modifier.fillMaxSize(),
            selectedNote = selectedNote,
            onEditTitle = onEditTitle,
            onEditSituation = onEditSituation,
            onEditBody = onEditBody
        )

    }
}

@Composable
fun NoteSheetHeader(
    modifier: Modifier = Modifier,
    operation: NoteOperation,
    onClickClose: () -> Unit,
    onClickSave: () -> Unit
){
    val title = getNoteHeaderTitle(operation)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small),
        verticalAlignment = Alignment.CenterVertically
    )
    {

        Icon(
            modifier = Modifier.size(MaterialTheme.dimens.icon),
            imageVector = ImageVector.vectorResource(R.drawable.ic_note),
            contentDescription = stringResource(R.string.note_screen_title),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.headlineLarge
        )

        IconButton(
            onClick = onClickSave
        ) {

            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.icon),
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(
                    id = R.string.note_forms_save_button,title
                ),
                tint = Green500
            )
        }

        IconButton(
            onClick = onClickClose
        ) {

            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.icon),
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(
                    id = R.string.note_screen_close_menu,title
                ),
                tint = RedBase
            )
        }
    }
}

@Composable
fun getNoteHeaderTitle(
    operation: NoteOperation
): String{
    return when(operation){
        NoteOperation.OnInsert -> stringResource(R.string.note_operation_insert)
        NoteOperation.OnUpdate -> stringResource(R.string.note_operation_edit)
        NoteOperation.None -> ""
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
        selectedOperation = NoteOperation.OnInsert,
        onEditTitle = { note = note.copy(title = it) },
        onEditSituation = { note = note.copy(situation = it) },
        onEditBody = { note = note.copy(body = it) },
        onClickClose = {},
        onClickSave = {}
    )
}



