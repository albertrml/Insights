package br.com.arml.insights.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import br.com.arml.insights.ui.screen.splash.SplashScreen
import br.com.arml.insights.ui.screen.tag.TagScreen

@Composable
fun InsightRoute(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavHost(
        navController = navController,
        startDestination = SplashScreenDestination.route,
        modifier = modifier
    ){
        composable(route = SplashScreenDestination.route){
            SplashScreen(
                modifier = modifier,
                onNavigateTo = {
                    navController.navigate(TagScreenDestination.route) {
                        popUpTo(SplashScreenDestination.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = TagScreenDestination.route){
            TagScreen(
                modifier = modifier,
                onNavigateTo = { }
            )
        }
    }
}