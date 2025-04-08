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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.ui.component.HeaderScreen
import br.com.arml.insights.ui.component.InsightButton
import br.com.arml.insights.ui.component.tag.TagCard
import br.com.arml.insights.ui.theme.Gray300

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagScreen(
    modifier: Modifier = Modifier,
){

    val bottomSheetState = rememberBottomSheetScaffoldState()
    val configuration = LocalConfiguration.current
    var selectTag by rememberSaveable { mutableStateOf<Tag?>(null) }


    val targetPeekHeight = if(selectTag != null) configuration.screenHeightDp.dp * 0.7f else 0.dp
    val animatedPeekHeight by animateFloatAsState(
        targetValue = targetPeekHeight.value,
        animationSpec = tween(durationMillis = 500),
    )

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetPeekHeight = animatedPeekHeight.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            selectTag?.let { tag ->
                FooterScreen(
                    modifier = modifier.padding(horizontal = 8.dp),
                    selectedTag = tag,
                    onClickClose = { selectTag = null }
                )
            }
        },
    ) { padding ->
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            HeaderScreen(
                title = stringResource(R.string.tag_screen_title),
                modifier = Modifier.padding(
                    top = 24.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                ),
                onAddItem = {/* TODO */ }
            )
            BodyScreen(
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                onSelectedTag = { tag -> selectTag = tag },
            )
        }
    }
}

@Composable
fun BodyScreen(
    modifier: Modifier = Modifier,
    onSelectedTag: (Tag) -> Unit = {},
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TagList(
            tagList = mockTags,
            onSelectedTag = onSelectedTag
        )
    }
}

@Composable
fun TagList(
    modifier: Modifier = Modifier,
    tagList: List<Tag>,
    onSelectedTag: (Tag) -> Unit,
){
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(tagList) { tag ->
            TagCard(
                modifier = modifier.padding(horizontal = 8.dp),
                tag = tag,
                onEditTag = { onSelectedTag(tag) },
                onDeleteTag = { onSelectedTag(tag) },
                onNavigationTo = { onSelectedTag(tag) }
            )
        }
    }
}


@Composable
fun FooterScreen(
    modifier: Modifier = Modifier,
    selectedTag: Tag,
    onClickClose: () -> Unit = {}
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
                text = selectedTag.name,
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
                        selectedTag.name
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

        TagAction(
            action = stringResource(id = R.string.tag_screen_see_insights_menu, selectedTag.name),
            modifier = modifier.fillMaxWidth(),
            tag = selectedTag
        )

        TagAction(
            action = stringResource(id = R.string.tag_screen_edit_menu),
            modifier = modifier.fillMaxWidth(),
            tag = selectedTag
        )

        TagAction(
            action = stringResource(id = R.string.tag_screen_remove_menu),
            modifier = modifier.fillMaxWidth(),
            tag = selectedTag
        )
    }
}

@Composable
fun TagAction(
    tag: Tag,
    action: String,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {}
){

    InsightButton(
        modifier = modifier,
        text = action,
        iconRes = null,
        onClick = { onClick(tag.id) }
    )
}



@Preview
@Composable
fun TagScreenPreview(){
    TagScreen()
}