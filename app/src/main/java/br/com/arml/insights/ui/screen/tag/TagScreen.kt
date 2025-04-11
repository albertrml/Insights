package br.com.arml.insights.ui.screen.tag

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.ui.component.general.HeaderScreen
import br.com.arml.insights.ui.component.tag.TagBodyContent
import br.com.arml.insights.ui.component.tag.TagSheetContent
import br.com.arml.insights.ui.component.tag.TagDeleteAlert

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagScreen(
    modifier: Modifier = Modifier,
){

    val bottomSheetState = rememberBottomSheetScaffoldState()
    var selectTag by rememberSaveable { mutableStateOf<TagUi?>(null) }
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showAlertDialog by rememberSaveable { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val targetPeekHeight = if(showBottomSheet) configuration.screenHeightDp.dp * 1f else 0.dp
    val animatedPeekHeight by animateFloatAsState(
        targetValue = targetPeekHeight.value,
        animationSpec = tween(durationMillis = 500),
    )

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = bottomSheetState,
        sheetPeekHeight = animatedPeekHeight.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            selectTag?.let { tag ->
                TagSheetContent(
                    modifier = modifier.padding(horizontal = 8.dp),
                    selectedTagUi = tag,
                    onUpdateTagUi = { updateTagUi -> selectTag = updateTagUi },
                    onClickClose = {
                        showBottomSheet = !showBottomSheet
                        selectTag = null
                    },
                    onClickSave = { }
                )
            }
        },
    ) { padding ->
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            TagDeleteAlert(
                modifier = Modifier,
                tagName = selectTag?.name ?: "",
                showDialog = showAlertDialog,
                onDismissRequest = {
                    selectTag = null
                    showAlertDialog = !showAlertDialog
                },
                onConfirmation = {
                    selectTag = null
                    showAlertDialog = !showAlertDialog
                }
            )
            HeaderScreen(
                title = stringResource(R.string.tag_screen_title),
                modifier = Modifier.padding(
                    top = 24.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                ),
                onAddItem = {
                    selectTag = TagUi.fromTag(null)
                    showBottomSheet = !showBottomSheet
                }
            )
            TagBodyContent(
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                onEditTagUi = { tag ->
                    selectTag = tag
                    showBottomSheet = !showBottomSheet
                },
                onDeleteTag = { tag ->
                    selectTag = tag
                    showAlertDialog = !showAlertDialog
                }
            )
        }
    }
}

@Preview
@Composable
fun TagScreenPreview(){
    TagScreen()
}