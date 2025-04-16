package br.com.arml.insights.ui.component.tag

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.arml.insights.R
import br.com.arml.insights.ui.component.common.InsightAlertDialog

@Composable
fun TagDeleteAlert(
    modifier: Modifier = Modifier,
    tagName: String,
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
){
    if (showDialog) {
        InsightAlertDialog(
            modifier = modifier,
            dialogTitle = tagName,
            dialogText = stringResource(R.string.tag_scree_alert_dialog_message),
            onDismissRequest = onDismissRequest,
            onConfirmation = onConfirmation
        )
    }
}