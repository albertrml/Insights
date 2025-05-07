package br.com.arml.insights.ui.screen.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.ui.component.common.HeaderScreen
import br.com.arml.insights.ui.component.common.InsightErrorSnackBar
import br.com.arml.insights.ui.component.common.InsightFilterAndSort
import br.com.arml.insights.ui.component.tag.TagBodyContent
import br.com.arml.insights.ui.component.tag.TagDeleteAlert
import br.com.arml.insights.ui.component.tag.TagSheetContent
import br.com.arml.insights.ui.theme.dimens
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagScreen(
    modifier: Modifier = Modifier,
    onNavigateTo: (Int, String) -> Unit,
){
    val viewModel = hiltViewModel<TagViewModel>()
    val tagState by viewModel.state.collectAsStateWithLifecycle()
    val tagScreenState = rememberTagScreenState()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            effect?.let { tagScreenState.onEffect(it) }
        }
    }

    BottomSheetScaffold(
        modifier = modifier
            .consumeWindowInsets(WindowInsets.safeDrawing)
            .fillMaxSize(),
        scaffoldState = tagScreenState.bottomSheetState,
        sheetSwipeEnabled = false,
        sheetPeekHeight = tagScreenState.getAnimatedSheetPeekHeight(),
        sheetShape = RoundedCornerShape(
            topStart = MaterialTheme.dimens.medium,
            topEnd = MaterialTheme.dimens.medium
        ),
        snackbarHost = {
            InsightErrorSnackBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.medium)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                hostState = tagScreenState.bottomSheetState.snackbarHostState
            )
        },
        sheetContent = {
            tagState.selectedTagUi?.let { selectedTagUi ->
                TagSheetContent(
                    modifier = modifier
                        .padding(horizontal = MaterialTheme.dimens.medium)
                        .windowInsetsPadding(WindowInsets.navigationBars),
                    selectedTagUi = selectedTagUi,
                    onEditName = { viewModel.onEvent(TagEvent.OnEditName(it)) },
                    onEditDescription = { viewModel.onEvent(TagEvent.OnEditDescription(it)) },
                    onEditColor = { viewModel.onEvent(TagEvent.OnEditColor(it)) },
                    onClickClose = {
                        viewModel.onEvent(TagEvent.OnClickToCloseSheet)
                    },
                    onClickSave = {
                        viewModel.onEvent(TagEvent.OnInsertOrUpdate(tagState.selectedOperation))
                    }
                )
            }
        },
    ) { padding ->
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.dimens.medium)
                .consumeWindowInsets(padding),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium)
        ) {

            HeaderScreen(
                title = stringResource(R.string.tag_screen_title),
                iconResId = R.drawable.ic_tag,
                modifier = Modifier.padding(top = MaterialTheme.dimens.large),
                onAddItem = {
                    viewModel.onEvent(TagEvent.OnClickToOpenSheet(null,TagOperation.OnInsert))
                }
            )

            InsightFilterAndSort(
                modifier = modifier,
                sortedBy = { ascending ->
                    if (ascending)
                        viewModel.onEvent(TagEvent.OnSortTagsByNameAscending)
                    else
                        viewModel.onEvent(TagEvent.OnSortTagsByNameDescending)
                },
                searchQuery = tagScreenState.searchQuery,
                onSearchTextChange = {
                    tagScreenState.searchQuery = it
                    viewModel.onEvent(TagEvent.OnSearch(it))
                },
                onRefreshTags = {
                    tagScreenState.searchQuery = ""
                    viewModel.onEvent(TagEvent.OnFetchAllItems)
                }
            )

            TagBodyContent(
                modifier = modifier,
                tags = tagState.tags,
                onDeleteTag = {
                    viewModel.onEvent(
                        TagEvent.OnClickToShowDeleteDialog(selectedTag = it)
                    )
                },
                onEditTagUi = { selectedTagUi ->
                    viewModel.onEvent(
                        TagEvent.OnClickToOpenSheet(selectedTagUi,TagOperation.OnUpdate)
                    )
                },
                onNavigationTo = { selectedTagUi ->
                    onNavigateTo(selectedTagUi.id,selectedTagUi.name)
                }
            )

            TagDeleteAlert(
                modifier = modifier,
                tagName = (tagState.selectedTagUi?:TagUi.fromTag(null)).name,
                showDialog = tagScreenState.isAlertDialogVisible,
                onDismissRequest = {
                    viewModel.onEvent(TagEvent.OnClickToCloseDeleteDialog)
                },
                onConfirmation = {
                    viewModel.onEvent(TagEvent.OnDelete)
                }
            )
        }
    }
}
