package br.com.arml.insights.ui.screen.common

import android.os.Build
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemGestures
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

@Composable
fun calculateContentSheetPeekHeight(
    isVisibleContentSheet: Boolean,
): Float {
    return if (isVisibleContentSheet) {
        val density = LocalDensity.current
        val containerHeightPx = LocalWindowInfo.current.containerSize.height

        with(density) { containerHeightPx.toDp() }.value
    } else {
        0.0f
    }
}

@Composable
fun rememberAnimatedSheetPeekHeight(isVisibleContentSheet: Boolean) = animateFloatAsState(
    targetValue = calculateContentSheetPeekHeight(isVisibleContentSheet),
    animationSpec = tween(durationMillis = 500),
).value.dp

@Composable
fun Modifier.setMargin(padding: PaddingValues = PaddingValues(0.dp)): Modifier {
    val modifier =  this
        .windowInsetsPadding(WindowInsets.systemBars)
        .windowInsetsPadding(WindowInsets.displayCutout)
        .windowInsetsPadding(WindowInsets.navigationBars)
        .windowInsetsPadding(WindowInsets.systemGestures)
    return if (!isEmulator()) { modifier.padding(padding) } else { modifier }
}

fun isEmulator(): Boolean {
    return (Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
            || "google_sdk" == Build.PRODUCT)
}