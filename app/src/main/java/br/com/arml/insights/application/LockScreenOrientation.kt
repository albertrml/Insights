package br.com.arml.insights.application

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

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

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Composable
fun HideStatusBarSystem(
    content: @Composable () -> Unit
){

    val view = LocalView.current

    if (!view.isInEditMode) {
        val currentWindow = (view.context as? Activity)?.window ?: return

        SideEffect {
            // Draw behind the status bar
            WindowCompat.setDecorFitsSystemWindows(currentWindow, false)
            // Make the status bar transparent
            //currentWindow.statusBarColor = Color.Transparent.hashCode()
            view.setBackgroundColor(Color.Transparent.hashCode())

            // Access WindowInsetsController for finer control
            val windowInsetsController = WindowCompat.getInsetsController(currentWindow, view)

            // Hide the status bar
            windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())

            // Optional: Control the behavior of the status bar when it reappears (e.g., after a swipe)
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

            // Optional: Set the status bar icons to be light or dark
            windowInsetsController.isAppearanceLightStatusBars =
                true // Set to true if your background is dark, false if it's light
        }
    }

    content()

}