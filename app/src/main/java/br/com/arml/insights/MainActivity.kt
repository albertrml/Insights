package br.com.arml.insights

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import br.com.arml.insights.application.HideStatusBarSystem
import br.com.arml.insights.application.LockScreenOrientation
import br.com.arml.insights.ui.navigation.InsightRoute
import br.com.arml.insights.ui.theme.InsightsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            HideStatusBarSystem {
                InsightsTheme {
                    val navController = rememberNavController()
                    InsightRoute(
                        navController = navController,
                    )
                }
            }
        }
    }
}