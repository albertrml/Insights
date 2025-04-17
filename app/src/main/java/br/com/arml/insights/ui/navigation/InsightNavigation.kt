package br.com.arml.insights.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import br.com.arml.insights.ui.screen.note.NoteScreen
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
                onNavigateTo = { tagId,tagName ->
                    navController.navigate("${NoteScreenDestination.route}/$tagId/$tagName"){
                        popUpTo(TagScreenDestination.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(
            route = NoteScreenDestination.routeWithArgs,
            arguments = NoteScreenDestination.arguments,
            deepLinks = NoteScreenDestination.deepLink
        ){ navBackStackEntry ->
            val tagId = navBackStackEntry
                .arguments?.getInt(NoteScreenDestination.tagIdArg)?:0

            val tagName = navBackStackEntry
                .arguments?.getString(NoteScreenDestination.tagNameArg)?:""
            NoteScreen(
                modifier = modifier,
                tagId = tagId,
                tagName = tagName,
                onNavigateTo = {
                    navController.navigate(TagScreenDestination.route){
                        popUpTo(TagScreenDestination.route) { inclusive = true }
                    }
                }
            )
        }
    }
}