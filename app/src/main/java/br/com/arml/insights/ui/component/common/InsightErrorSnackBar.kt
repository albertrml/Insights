package br.com.arml.insights.ui.component.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

@Composable
fun InsightErrorSnackBar(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState,
){
    SnackbarHost(
        hostState = hostState,
        modifier = modifier,
        snackbar = { snackBarData ->
            Popup(
                alignment = Alignment.BottomCenter,
                onDismissRequest = { snackBarData.dismiss() }
            ) {
                Snackbar(
                    snackbarData = snackBarData,
                    shape = RoundedCornerShape(8.dp),
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                )
            }
        }
    )
}