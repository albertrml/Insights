package br.com.arml.insights.ui.theme


import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import br.com.arml.insights.MainActivity

private val LightColors = lightColorScheme(
    primary = BlueBase,
    onPrimary = Color.White,
    primaryContainer = BlueLight,
    onPrimaryContainer = Gray600,

    secondary = BlueBase,
    onSecondary = Color.White,
    secondaryContainer = BlueLight,
    onSecondaryContainer = Gray600,

    tertiary = BlueDark,
    onTertiary = Color.White,
    tertiaryContainer = BlueLight,
    onTertiaryContainer = Gray600,

    background = Gray100,
    onBackground = Gray600,

    surface = Gray100,
    onSurface = Gray600,
    surfaceVariant = Gray200,
    onSurfaceVariant = Gray500,

    error = RedBase,
    onError = Color.White,
    errorContainer = RedLight,
    onErrorContainer = Gray600,

    outline = Gray500,
)

private val DarkColors = darkColorScheme(
    primary = BlueBase,
    onPrimary = Color.White,
    primaryContainer = BlueDark,
    onPrimaryContainer = BlueLight,

    secondary = BlueBase,
    onSecondary = Color.White,
    secondaryContainer = BlueDark,
    onSecondaryContainer = BlueLight,

    tertiary = BlueLight,
    onTertiary = Color.Black,
    tertiaryContainer = BlueDark,
    onTertiaryContainer = BlueLight,

    background = Gray600,
    onBackground = Gray100,

    surface = Gray600,
    onSurface = Gray100,
    surfaceVariant = Gray500,
    onSurfaceVariant = Gray200,

    error = RedBase,
    onError = Color.White,
    errorContainer = RedSDark,
    onErrorContainer = Gray200,

    outline = Gray100,
)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun InsightsTheme(
    darkTheme:Boolean = isSystemInDarkTheme(),
    activity: Activity = LocalActivity.current as MainActivity,
    content: @Composable () -> Unit
){
    val colors = if (darkTheme) DarkColors else LightColors
    val windowInfo = calculateWindowSizeClass(activity = activity)
    val config = LocalConfiguration.current


    val (appDimens, typography) = when(windowInfo.widthSizeClass){
        WindowWidthSizeClass.Compact -> {
            when(config.screenHeightDp){
                in 0..360 -> { CompactSmallDimens to CompactSmallTypography }
                in 361..480 -> { CompactMediumDimens to CompactMediumTypography }
                else -> { CompactDimens to CompactTypography }
            }
        }
        WindowWidthSizeClass.Medium -> { MediumDimens to MediumTypography }
        else ->  { LargeDimens to LargeTypography }
    }

    AppUtils(
        appDimens = appDimens,
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = typography,
            content = content
        )
    }

}

val MaterialTheme.dimens
    @Composable
    get() = LocalAppDimens.current