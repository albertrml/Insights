package br.com.arml.insights.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    // Corner radius
    val smallCornerRadius: Dp = 8.dp,
    val mediumCornerRadius: Dp = 12.dp,
    val largeCornerRadius: Dp = 16.dp,
    // Elevation
    val smallElevation: Dp = 0.dp,
    val mediumElevation: Dp = 0.dp,
    val largeElevation: Dp = 0.dp,
    // Icon
    val smallIcon: Dp = 42.dp,
    val mediumIcon: Dp = 68.dp,
    val largeIcon: Dp = 90.dp,
    val xLargeIcon: Dp = 180.dp,
    // Margin
    val smallMargin: Dp = 0.dp,
    val mediumMargin: Dp = 0.dp,
    val largeMargin: Dp = 0.dp,
    val xLargeMargin: Dp = 0.dp,
    // Padding
    val smallPadding: Dp = 0.dp,
    val mediumPadding: Dp = 0.dp,
    val largePadding: Dp = 0.dp,
    // Spacing
    val smallSpacing: Dp = 0.dp,
    val mediumSpacing: Dp = 0.dp,
    val largeSpacing: Dp = 0.dp,
    // Thickness
    val smallThickness: Dp = 0.dp,
    val mediumThickness: Dp = 0.dp,
    val largeThickness: Dp = 0.dp,
    // HsvColorPicker
    val colorPickerHeight: Dp = 225.dp,
    // Slider height
    val sliderHeight: Dp = 32.dp,
    val sliderWheelRadius: Dp = 16.dp,
    val outlinedTextFieldTopPadding: Dp = 4.dp
)

// Width < 600: Phone in portrait
val compactDimens = Dimens(
    // Elevation
    smallElevation = 0.dp,
    mediumElevation = 1.dp,
    largeElevation = 2.dp,
    // Icon
    smallIcon = 42.dp,
    mediumIcon = 50.dp,
    largeIcon = 80.dp,
    xLargeIcon = 120.dp,
    // Margin
    smallMargin = 8.dp,
    mediumMargin = 16.dp,
    largeMargin = 24.dp,
    xLargeMargin = 32.dp,
    // Padding
    smallPadding = 8.dp,
    mediumPadding = 16.dp,
    largePadding = 16.dp,
    // Spacing
    smallSpacing = 4.dp,
    mediumSpacing = 8.dp,
    largeSpacing = 12.dp,
    // Thickness
    smallThickness = 2.dp,
    mediumThickness = 3.dp,
    largeThickness = 4.dp
)

/* 600 ≤ width < 840:
*   - Tablet in portrait
*   - Foldable in portrait (unfolded)
 */
val mediumDimens = Dimens(
    // Elevation
    smallElevation = 1.dp,
    mediumElevation = 2.dp,
    largeElevation = 3.dp,
    // Icon
    smallIcon = 42.dp,
    mediumIcon = 60.dp,
    largeIcon = 100.dp,
    xLargeIcon = 120.dp,
    // Margin
    smallMargin = 8.dp,
    mediumMargin = 16.dp,
    largeMargin = 24.dp,
    xLargeMargin = 32.dp,
    // Padding
    smallPadding = 8.dp,
    mediumPadding = 12.dp,
    largePadding = 16.dp,
    // Spacing
    smallSpacing = 8.dp,
    mediumSpacing = 12.dp,
    largeSpacing = 16.dp,
    // Thickness
    smallThickness = 2.dp,
    mediumThickness = 3.dp,
    largeThickness = 4.dp
)

/* 840 ≤ width < 1200:
*   - Phone in landscape
*   - Tablet in landscape
*   - Foldable in landscape (unfolded)
*   - Desktop
*/
val expandedDimens = Dimens(
    // Elevation
    smallElevation = 2.dp,
    mediumElevation = 3.dp,
    largeElevation = 4.dp,
    // Icon
    smallIcon = 42.dp,
    mediumIcon = 60.dp,
    largeIcon = 130.dp,
    xLargeIcon = 180.dp,
    // Margin
    smallMargin = 8.dp,
    mediumMargin = 16.dp,
    largeMargin = 24.dp,
    xLargeMargin = 32.dp,
    // Padding
    smallPadding = 8.dp,
    mediumPadding = 12.dp,
    largePadding = 16.dp,
    // Spacing
    smallSpacing = 8.dp,
    mediumSpacing = 12.dp,
    largeSpacing = 16.dp,
    // Thickness
    smallThickness = 3.dp,
    mediumThickness = 4.dp,
    largeThickness = 5.dp
)

/* 1200 ≤ width:
*   - Phone in landscape
*   - Tablet in landscape
*   - Foldable in landscape (unfolded)
*   - Desktop
*/
val largeDimens = Dimens(
    // Elevation
    smallElevation = 3.dp,
    mediumElevation = 4.dp,
    largeElevation = 5.dp,
    // Icon
    smallIcon = 42.dp,
    mediumIcon = 60.dp,
    largeIcon = 120.dp,
    xLargeIcon = 180.dp,
    // Margin
    smallMargin = 12.dp,
    mediumMargin = 20.dp,
    largeMargin = 28.dp,
    xLargeMargin = 36.dp,
    // Padding
    smallPadding = 12.dp,
    mediumPadding = 18.dp,
    largePadding = 24.dp,
    // Spacing
    smallSpacing = 12.dp,
    mediumSpacing = 18.dp,
    largeSpacing = 24.dp,
    // Thickness
    smallThickness = 4.dp,
    mediumThickness = 5.dp,
    largeThickness = 6.dp
)