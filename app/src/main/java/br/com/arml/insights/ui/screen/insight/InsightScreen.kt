@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package br.com.arml.insights.ui.screen.insight

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.rememberPaneExpansionState
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import br.com.arml.insights.ui.screen.common.setMargin
import br.com.arml.insights.ui.screen.note.NoteScreen
import br.com.arml.insights.ui.screen.tag.TagScreen
import br.com.arml.insights.ui.theme.dimens
import kotlinx.coroutines.launch

@Composable
fun InsightScreen(
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val navigator = rememberListDetailPaneScaffoldNavigator()
    val paneExpansionState = rememberPaneExpansionState()

    Box(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {
        NavigableListDetailPaneScaffold(
            modifier = Modifier,
            navigator = navigator,
            paneExpansionState = paneExpansionState,
            listPane = {
                paneExpansionState.setFirstPaneProportion(0.5f)
                AnimatedPane {
                    TagScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .setMargin()
                            .padding(horizontal = MaterialTheme.dimens.smallMargin),
                        onNavigateTo = { tagId, tagName ->
                            scope.launch {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail,
                                    contentKey = tagId to tagName
                                )
                            }
                        }
                    )
                }
            },
            detailPane = {
                AnimatedPane {
                    navigator.currentDestination?.contentKey?.let{
                        NoteScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .setMargin()
                                .padding(horizontal = MaterialTheme.dimens.smallMargin),
                            tagId = (it as Pair<*, *>).first as Int,
                            tagName = it.second as String,
                            onNavigateTo = {
                                scope.launch {
                                    navigator.navigateBack()
                                }
                            }
                        )
                    }
                }
            }
        )
    }
}