package br.com.arml.insights.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val smallThickness: Dp = 2.dp,
    val mediumThickness: Dp = 4.dp,
    val icon: Dp = 42.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
)

val CompactSmallDimens = Dimens(
    smallThickness = 1.dp,
    mediumThickness = 2.dp,
    icon = 32.dp,
    small = 4.dp,
    medium = 8.dp,
    large = 16.dp,
)

val CompactMediumDimens = Dimens(
    smallThickness = 2.dp,
    mediumThickness = 4.dp,
    icon = 42.dp,
    small = 8.dp,
    medium = 16.dp,
    large = 24.dp,
)

val CompactDimens = Dimens(
    smallThickness = 2.dp,
    mediumThickness = 4.dp,
    icon = 42.dp,
    small = 8.dp,
    medium = 16.dp,
    large = 24.dp,
)

val MediumDimens = Dimens(
    smallThickness = 2.dp,
    mediumThickness = 4.dp,
    icon = 42.dp,
    small = 8.dp,
    medium = 16.dp,
    large = 32.dp,
)

val LargeDimens = Dimens(
    smallThickness = 4.dp,
    mediumThickness = 8.dp,
    icon = 64.dp,
    small = 16.dp,
    medium = 24.dp,
    large = 48.dp,
)
