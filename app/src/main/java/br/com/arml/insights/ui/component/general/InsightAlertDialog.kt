package br.com.arml.insights.ui.component.general

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.insights.R
import br.com.arml.insights.ui.theme.Green500
import br.com.arml.insights.ui.theme.RedBase

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
            InsightButton(
                text = stringResource(R.string.alert_dialog_confirm_button),
                onClick = onConfirmation,
                color = Green500
            )
        },
        dismissButton = {
            InsightButton(
                text = stringResource(R.string.alert_dialog_dismiss_button),
                onClick = onConfirmation,
                color = RedBase
            )
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