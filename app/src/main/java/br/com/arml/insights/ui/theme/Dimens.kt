package br.com.arml.insights.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val extraSmallThickness: Dp = 1.dp,
    val smallThickness: Dp = 2.dp,
    val mediumThickness: Dp = 4.dp,
    val smallCornerRadius: Dp = 4.dp,
    val mediumCornerRadius: Dp = 8.dp,
    val largeCornerRadius: Dp = 16.dp,
    val cardElevation: Dp = 8.dp,
    val icon: Dp = 42.dp,
    val hsvColorPicker: Dp = 225.dp,
    val noteSize: Dp = 180.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp
)

val CompactSmallDimens = Dimens(
    extraSmallThickness = 1.dp,
    smallThickness = 1.dp,
    mediumThickness = 2.dp,
    icon = 32.dp,
    hsvColorPicker = 150.dp,
    noteSize = 100.dp,
    small = 4.dp,
    medium = 8.dp,
    large = 16.dp,
    extraLarge = 24.dp
)

val CompactMediumDimens = Dimens(
    extraSmallThickness = 1.dp,
    smallThickness = 2.dp,
    mediumThickness = 4.dp,
    icon = 42.dp,
    hsvColorPicker = 175.dp,
    noteSize = 120.dp,
    small = 8.dp,
    medium = 16.dp,
    large = 24.dp,
    extraLarge = 32.dp
)

val CompactDimens = Dimens(
    extraSmallThickness = 1.dp,
    smallThickness = 2.dp,
    mediumThickness = 4.dp,
    icon = 42.dp,
    hsvColorPicker = 200.dp,
    noteSize = 150.dp,
    small = 8.dp,
    medium = 16.dp,
    large = 24.dp,
    extraLarge = 32.dp
)

val MediumDimens = Dimens(
    extraSmallThickness = 1.dp,
    smallThickness = 2.dp,
    mediumThickness = 4.dp,
    icon = 42.dp,
    hsvColorPicker = 225.dp,
    noteSize = 180.dp,
    small = 8.dp,
    medium = 16.dp,
    large = 32.dp,
    extraLarge = 40.dp
)

val LargeDimens = Dimens(
    extraSmallThickness = 2.dp,
    smallThickness = 4.dp,
    mediumThickness = 8.dp,
    icon = 64.dp,
    hsvColorPicker = 400.dp,
    noteSize = 250.dp,
    small = 16.dp,
    medium = 24.dp,
    large = 48.dp,
    extraLarge = 56.dp
)
