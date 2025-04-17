package br.com.arml.insights.ui.component.note

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.ui.component.common.InsightAlertDialog

@Composable
fun NoteDeleteAlert(
    modifier: Modifier = Modifier,
    note: NoteUi,
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
){
    if (showDialog){
        InsightAlertDialog(
            modifier = modifier,
            dialogTitle = note.title,
            dialogText = stringResource(
                R.string.note_screen_alert_dialog_message,
                note.title,
                note.getCreationDate()
            ),
            onDismissRequest = onDismissRequest,
            onConfirmation = onConfirmation
        )
    }
}