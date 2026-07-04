package br.com.arml.insights.application

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import br.com.arml.insights.utils.tools.findActivity

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    LaunchedEffect(orientation, context) {
        val activity = context.findActivity() ?: return@LaunchedEffect
        val originalOrientation = activity.requestedOrientation
        if (originalOrientation != orientation){
            activity.requestedOrientation = orientation
        }
    }
}