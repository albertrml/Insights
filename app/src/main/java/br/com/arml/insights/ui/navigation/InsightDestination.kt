package br.com.arml.insights.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import br.com.arml.insights.R

interface InsightDestination {
    val iconResId: Int
    val route: String
}

object SplashScreenDestination : InsightDestination {
    override val iconResId = R.drawable.ic_insights_foreground
    override val route = "splash_screen"
}

object TagScreenDestination : InsightDestination {
    override val iconResId = R.drawable.ic_tag
    override val route = "tag_screen"
}

object NoteScreenDestination : InsightDestination {
    override val iconResId = R.drawable.ic_note
    override val route = "note_screen"
    const val tagIdArg = "tag_id"
    const val tagNameArg = "tag_name"
    val routeWithArgs = "$route/{$tagIdArg}/{$tagNameArg}"
    val arguments = listOf(
        navArgument(tagIdArg) { type = NavType.IntType },
        navArgument(tagNameArg) { type = NavType.StringType }
    )
    val deepLink = listOf(
        navDeepLink { uriPattern = "$route/{$tagIdArg}/{$tagNameArg}" }
    )
}

