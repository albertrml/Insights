package br.com.arml.insights.ui.component.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.insights.R

@Composable
fun InsightAlertDialog(
    modifier: Modifier = Modifier,
    dialogTitle: String,
    dialogText: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {

    AlertDialog(
        modifier = modifier,
        title = {
            Text(
                text = dialogTitle,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        text = {
            Text(
                text = dialogText,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = onConfirmation,
            ) {
                Text( text = stringResource(R.string.alert_dialog_confirm_button) )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismissRequest,
            ) {
                Text( text = stringResource(R.string.alert_dialog_dismiss_button) )
            }
        }

    )

}

@Preview
@Composable
fun InsightAlertDialogPreview(){
    InsightAlertDialog(
        modifier = Modifier,
        dialogTitle = "Title",
        dialogText = "Text",
        onDismissRequest = {},
        onConfirmation = {}
    )
}