package br.com.arml.insights.ui.navigation

import br.com.arml.insights.R

interface InsightDestination {
    val iconResId: Int
    val route: String
}

object SplashScreenDestination : InsightDestination {
    override val iconResId = R.drawable.ic_insights_foreground
    override val route = "splash_screen"
}