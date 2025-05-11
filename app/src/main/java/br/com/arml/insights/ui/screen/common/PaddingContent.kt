package br.com.arml.insights.ui.screen.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import br.com.arml.insights.ui.theme.dimens


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
fun calculateTopPadding(padding: PaddingValues) = padding.calculateTopPadding() +
        WindowInsets.systemBars.asPaddingValues().calculateTopPadding() +
        MaterialTheme.dimens.medium

@Composable
fun calculateBottomPadding(padding: PaddingValues) = padding.calculateBottomPadding() +
        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() +
        MaterialTheme.dimens.medium