package br.com.arml.insights.ui.theme


import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import br.com.arml.insights.MainActivity


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun InsightsTheme(
    darkTheme:Boolean = isSystemInDarkTheme(),
    activity: Activity = LocalActivity.current as MainActivity,
    content: @Composable () -> Unit
){
    val appOrientation = LocalConfiguration.current.orientation
    val (appDimens, typography) = getDimensAndTypographyByScreenOrientation(
        screenOrientation = appOrientation,
        windowSizeClass = calculateWindowSizeClass(activity)
    )

    AppUtils(
        appDimens = appDimens,
    ) {
        MaterialTheme(
            colorScheme = getColorScheme(darkTheme),
            typography = typography,
            content = content
        )
    }

}

val MaterialTheme.dimens
    @Composable
    get() = LocalAppDimens.current