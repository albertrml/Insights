package br.com.arml.insights.ui.screen.tag

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.ui.component.InsightButton
import br.com.arml.insights.ui.theme.Gray300
import br.com.arml.insights.ui.theme.Gray500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagScreen(
    modifier: Modifier = Modifier,
){

    val bottomSheetState = rememberBottomSheetScaffoldState()
    val configuration = LocalConfiguration.current
    var selectTag by rememberSaveable { mutableStateOf<Tag?>(null) }


    val targetPeekHeight = if(selectTag != null) configuration.screenHeightDp.dp * 0.4f else 0.dp
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
                title = "Tags",
                modifier = Modifier.padding(
                    top = 24.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                ),
                onClick = {/* TODO */ }
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
fun HeaderScreen(
    modifier: Modifier = Modifier,
    title: String = "Tags",
    onClick: () -> Unit
){
    Column(
        modifier = modifier,
    ) {
        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = MaterialTheme.typography.headlineLarge
            )
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    modifier = Modifier.size(42.dp),
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add New Tag",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        HorizontalDivider(Modifier, 2.dp, Gray500)
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
                    contentDescription = "Close ${selectedTag.name} menu",
                    tint = Color.Black
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier,
            thickness = 2.dp,
            color = Gray300
        )



        TagAction(action = "See Insights", modifier = modifier.fillMaxWidth(), tag = selectedTag)
        TagAction(action = "Edit tag", modifier = modifier.fillMaxWidth(), tag = selectedTag)
        TagAction(action = "Remove tag", modifier = modifier.fillMaxWidth(), tag = selectedTag)
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

            TagItem(
                tag = tag,
                onSelectedTag = { onSelectedTag(tag) }
            )
        }
    }
}

@Composable
fun TagItem(
    modifier: Modifier = Modifier,
    tag: Tag,
    onSelectedTag: (Tag) -> Unit,
){
    OutlinedCard(
        elevation = CardDefaults.cardElevation(8.dp),

    ){
        Column (
            modifier = modifier
        ) {
            Text(
                text = tag.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 4.dp
                )
            )
            Text(
                text = tag.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            )

            AnimatedHorizontalDivider(Modifier.padding(horizontal = 16.dp), 4.dp, Color(tag.color))

            Button(
                onClick = { onSelectedTag(tag) },
                modifier = Modifier
                    .padding(
                        vertical = 8.dp,
                        horizontal = 16.dp,
                    )
                    .align(Alignment.End)
            ) {
                Text("Actions")
            }
        }
    }
}

@Composable
fun AnimatedHorizontalDivider(
    modifier: Modifier,
    thickness: Dp,
    endColor: Color,
){
    val infiniteTransition = rememberInfiniteTransition()
    val animationFloat = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val colorStart = infiniteTransition.animateColor(
        initialValue = Color.White,
        targetValue = endColor,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Upper Background color"
    )

    val colorEnd = infiniteTransition.animateColor(
        initialValue = endColor,
        targetValue = Color.White,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Upper Background color"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        colorStart.value,
                        colorEnd.value
                    )
                )
            )
            .rotate(animationFloat.value)
    ){ }
}

@Preview
@Composable
fun TagScreenPreview(){
    TagScreen()
}