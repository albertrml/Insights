package br.com.arml.insights.ui.theme

import android.content.res.Configuration
import androidx.compose.material3.Typography
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import kotlin.to

internal fun getDimensAndTypographyByScreenOrientation(
    screenOrientation: Int,
    windowSizeClass: WindowSizeClass
): Pair<Dimens, Typography> {
    return when (screenOrientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            return getDimensAndTypographyByWindowsHeightSize(windowSizeClass.heightSizeClass)
        }
        else -> {
            return getDimensAndTypographyByWindowWidthSize(windowSizeClass.widthSizeClass)
        }
    }
}

private fun getDimensAndTypographyByWindowWidthSize(
    windowWidthSizeClass: WindowWidthSizeClass
): Pair<Dimens, Typography> {
    return when (windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> compactDimens to smallTypography
        WindowWidthSizeClass.Medium -> mediumDimens to mediumTypography
        WindowWidthSizeClass.Expanded -> expandedDimens to expandedTypography
        else -> largeDimens to largeTypography
    }
}

private fun getDimensAndTypographyByWindowsHeightSize(
    windowHeightSizeClass: WindowHeightSizeClass
): Pair<Dimens, Typography> {
    return when (windowHeightSizeClass) {
        WindowHeightSizeClass.Compact -> compactDimens to smallTypography
        WindowHeightSizeClass.Medium -> mediumDimens to mediumTypography
        WindowHeightSizeClass.Expanded -> expandedDimens to expandedTypography
        else -> largeDimens to largeTypography
    }
}