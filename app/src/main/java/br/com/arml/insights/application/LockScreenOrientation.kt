package br.com.arml.insights.application

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import br.com.arml.insights.utils.tools.findActivity

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(orientation) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}