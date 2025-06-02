package br.com.arml.insights

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import br.com.arml.insights.application.HideStatusBarSystem
import br.com.arml.insights.application.LockScreenOrientation
import br.com.arml.insights.ui.navigation.InsightRoute
import br.com.arml.insights.ui.theme.InsightsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            val density = LocalDensity.current
            val screenHeightDp = with(density) { LocalWindowInfo.current.containerSize.height.toDp() }
            val screenWidthDp = with(density) { LocalWindowInfo.current.containerSize.width.toDp() }
            if(screenHeightDp < 1000.dp && screenWidthDp < 1000.dp)
                LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

            HideStatusBarSystem {
                InsightsTheme {
                    val navController = rememberNavController()
                    InsightRoute(
                        navController = navController
                    )
                }
            }
        }
    }
}