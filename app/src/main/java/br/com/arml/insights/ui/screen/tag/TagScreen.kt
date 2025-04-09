package br.com.arml.insights.ui.screen.tag

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.ui.component.HeaderScreen
import br.com.arml.insights.ui.component.InsightAlertDialog
import br.com.arml.insights.ui.component.tag.TagForms
import br.com.arml.insights.ui.component.tag.TagList
import br.com.arml.insights.ui.theme.Gray300

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagScreen(
    modifier: Modifier = Modifier,
){

    val bottomSheetState = rememberBottomSheetScaffoldState()
    val configuration = LocalConfiguration.current
    var selectTag by rememberSaveable { mutableStateOf<TagUi?>(null) }
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showAlertDialog by rememberSaveable { mutableStateOf(false) }

    val targetPeekHeight = if(showBottomSheet) configuration.screenHeightDp.dp * 1f else 0.dp
    val animatedPeekHeight
    by animateFloatAsState(
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
                FooterScreen(
                    modifier = modifier.padding(horizontal = 8.dp),
                    selectedTagUi = tag,
                    onChangeTagUi = { tagUi -> selectTag = tagUi },
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
            AlertDeleteTagUi(
                modifier = Modifier,
                tagName = selectTag?.name ?: "",
                showDialog = showAlertDialog,
                onChangeShowDialog = { showAlertDialog = it },
                onDismissRequest = {
                    selectTag = null
                },
                onConfirmation = {
                    selectTag = null
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

                }
            )
            BodyScreen(
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                onEditTagUi = { tag ->
                    showBottomSheet = !showBottomSheet
                    selectTag = tag
                },
                onDeleteTag = { tag ->
                    showAlertDialog = !showAlertDialog
                    selectTag = tag
                }
            )
        }
    }
}

@Composable
fun AlertDeleteTagUi(
    modifier: Modifier = Modifier,
    tagName: String,
    showDialog: Boolean,
    onChangeShowDialog: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
){
    if (showDialog) {
        InsightAlertDialog(
            modifier = modifier,
            dialogTitle = tagName,
            dialogText = stringResource(R.string.tag_scree_alert_dialog_message),
            onDismissRequest = {
                onChangeShowDialog(!showDialog)
                onDismissRequest },
            onConfirmation = {
                onChangeShowDialog(!showDialog)
                onConfirmation
            }
        )
    }
}

@Composable
fun BodyScreen(
    modifier: Modifier = Modifier,
    onEditTagUi: (TagUi) -> Unit,
    onDeleteTag: (TagUi) -> Unit
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TagList(
            tagList = mockTags.map { TagUi.fromTag(it) },
            onEditTagUi = onEditTagUi,
            onDeleteTagUi = onDeleteTag
        )
    }
}


@Composable
fun FooterScreen(
    modifier: Modifier = Modifier,
    selectedTagUi: TagUi,
    onChangeTagUi: (TagUi) -> Unit = {},
    onClickClose: () -> Unit = {},
    onClickSave: (TagUi) -> Unit = {}
){

    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        Row(
            modifier = modifier
        )
        {
            Text(
                modifier = Modifier.weight(1f),
                text = selectedTagUi.name,
                style = MaterialTheme.typography.headlineLarge
            )
            IconButton(
                onClick = onClickClose
            ) {
                Icon(
                    modifier = Modifier.size(42.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(
                        id = R.string.tag_screen_close_menu,
                        selectedTagUi.name
                    ),
                    tint = Color.Black
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier,
            thickness = 2.dp,
            color = Gray300
        )

        TagForms(
            modifier = modifier,
            tagUi = selectedTagUi,
            onEditName = { onChangeTagUi(it) },
            onEditDescription = { onChangeTagUi(it) },
            onEditColor = { onChangeTagUi(it) },
            onClickSave = { onClickSave(it) }
        )

    }
}



@Preview
@Composable
fun TagScreenPreview(){
    TagScreen()
}