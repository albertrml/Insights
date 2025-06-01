package br.com.arml.insights.ui.screen.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
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
fun Modifier.setMargin(): Modifier {
    return this.windowInsetsPadding(WindowInsets.systemBars)
        .windowInsetsPadding(WindowInsets.displayCutout)
        .windowInsetsPadding(WindowInsets.navigationBars)
        .windowInsetsPadding(WindowInsets.systemGestures)
}