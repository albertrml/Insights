package br.com.arml.insights.ui.component.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.window.Popup
import br.com.arml.insights.ui.theme.dimens

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
                    shape = RoundedCornerShape(MaterialTheme.dimens.mediumCornerRadius),
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                )
            }
        }
    )
}

@Preview(
    name = "compact screen",
    showBackground = true,
    device = "spec:width=360dp,height=640dp")
@Preview(
    name = "medium screen",
    showBackground = true,
    device = "spec:width=412dp,height=924dp")
@Preview(
    name = "expanded screen",
    showBackground = true,
    device = "spec:width=800dp,height=1280dp")
@Preview(
    name = "compact screen",
    showBackground = true,
    device = "spec:width=640dp,height=360dp")
@Preview(
    name = "medium screen",
    showBackground = true,
    device = "spec:width=924dp,height=412dp")
@Preview(
    name = "expanded screen",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp")
@Composable
fun InsightErrorSnackBarPreview() {
    val snackBarHostState = SnackbarHostState()

    LaunchedEffect(Unit) {
        snackBarHostState.showSnackbar(
            message = LoremIpsum(1).toString(),
            withDismissAction = true // Opcional: para mostrar um botão de dispensar
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        InsightErrorSnackBar(
            hostState = snackBarHostState
        )
    }
}