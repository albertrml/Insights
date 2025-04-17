package br.com.arml.insights.ui.screen.tag

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.ui.component.common.HeaderScreen
import br.com.arml.insights.ui.component.common.InsightErrorSnackBar
import br.com.arml.insights.ui.component.tag.TagBodyContent
import br.com.arml.insights.ui.component.tag.TagSheetContent
import br.com.arml.insights.ui.component.tag.TagDeleteAlert
import br.com.arml.insights.ui.component.tag.TagFilterAndSort
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagScreen(
    modifier: Modifier = Modifier,
    onNavigateTo: (Int,String) -> Unit
){
    val viewModel = hiltViewModel<TagViewModel>()
    val tagState by viewModel.state.collectAsStateWithLifecycle()
    var isVisibleContentSheet by rememberSaveable { mutableStateOf(false) }
    var isAlertDialogVisible by rememberSaveable { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("")}

    val bottomSheetState = rememberBottomSheetScaffoldState()
    val configuration = LocalConfiguration.current
    val targetPeekHeight = if(isVisibleContentSheet) configuration.screenHeightDp.dp * 1f else 0.dp
    val animatedPeekHeight by animateFloatAsState(
        targetValue = targetPeekHeight.value,
        animationSpec = tween(durationMillis = 500),
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when(effect){
                is TagEffect.OnShowContentSheet -> {
                    isVisibleContentSheet = true
                }
                is TagEffect.OnHideBottomSheet -> {
                    isVisibleContentSheet = false
                }
                is TagEffect.OnShowDeleteDialog -> {
                    isAlertDialogVisible = true
                }
                is TagEffect.OnHideDeleteDialog -> {
                    isAlertDialogVisible = false
                }

                is TagEffect.ShowSnackBar -> {
                    bottomSheetState.snackbarHostState.showSnackbar(effect.message)
                }
                else -> {}
            }
        }
    }

    BottomSheetScaffold(
        modifier = modifier.padding(top = 16.dp),
        scaffoldState = bottomSheetState,
        sheetPeekHeight = animatedPeekHeight.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        snackbarHost = {
            InsightErrorSnackBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 32.dp),
                hostState = bottomSheetState.snackbarHostState
            )
        },
        sheetContent = {
            tagState.selectedTagUi?.let { selectedTagUi ->
                TagSheetContent(
                    modifier = modifier.padding(horizontal = 8.dp),
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
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            HeaderScreen(
                title = stringResource(R.string.tag_screen_title),
                iconResId = R.drawable.ic_tag,
                modifier = Modifier
                    .padding(top = 24.dp),
                onAddItem = {
                    viewModel.onEvent(TagEvent.OnClickToOpenSheet(null,TagOperation.OnInsert))
                }
            )

            TagFilterAndSort(
                modifier = modifier,
                sortedBy = { ascending ->
                    if (ascending)
                        viewModel.onEvent(TagEvent.OnSortTagsByNameAscending)
                    else
                        viewModel.onEvent(TagEvent.OnSortTagsByNameDescending)
                },
                searchQuery = searchQuery,
                onSearchTextChange = {
                    searchQuery = it
                    viewModel.onEvent(TagEvent.OnSearch(it))
                },
                onRefreshTags = {
                    searchQuery = ""
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

            TagDelete(
                modifier = modifier,
                tag = tagState.selectedTagUi?:TagUi.fromTag(null),
                isVisibility = isAlertDialogVisible,
                onDismissRequest = {
                    viewModel.onEvent(TagEvent.OnClickToCloseDeleteDialog)
                },
                onConfirmationRequest = {
                    viewModel.onEvent(TagEvent.OnDelete)
                }
            )
        }
    }
}

@Composable
fun TagDelete(
    modifier: Modifier = Modifier,
    tag : TagUi,
    isVisibility: Boolean,
    onDismissRequest: () -> Unit = {},
    onConfirmationRequest: () -> Unit = {},
){
    TagDeleteAlert(
        modifier = modifier,
        tagName = tag.name,
        showDialog = isVisibility,
        onDismissRequest = onDismissRequest,
        onConfirmation = onConfirmationRequest
    )
}
