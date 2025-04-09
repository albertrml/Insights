package br.com.arml.insights.ui.component.tag

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.ui.component.AnimatedHorizontalDivider
import br.com.arml.insights.ui.component.InsightButton
import br.com.arml.insights.ui.theme.Gray200

@Composable
fun TagCard(
    modifier: Modifier = Modifier,
    tagUi: TagUi,
    onEditTagUi: (TagUi) -> Unit = {},
    onDeleteTagUi: (TagUi) -> Unit = {},
    onNavigationTo: (TagUi) -> Unit = {}
){
    OutlinedCard(
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = modifier.padding(vertical = 8.dp, horizontal = 8.dp),
        ){
            TagCardHeader(modifier = modifier, tagUi = tagUi, onEditTag = onEditTagUi)

            HorizontalDivider(modifier = modifier.padding(vertical = 8.dp), color = Gray200)
            TagCardContent(modifier = modifier, bodyContent = tagUi.description)

            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            AnimatedHorizontalDivider(Modifier.padding(horizontal = 8.dp), 4.dp, tagUi.color)

            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            TagCardFoot(
                modifier = modifier,
                tagUi = tagUi,
                onDeleteTag = onDeleteTagUi,
                onNavigationTo = onNavigationTo
            )
        }
    }
}

@Composable
fun TagCardHeader(
    modifier: Modifier = Modifier,
    tagUi: TagUi,
    onEditTag: (TagUi) -> Unit = {},
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = tagUi.name,
            style = MaterialTheme.typography.titleLarge
        )
        IconButton(
            onClick = { onEditTag(tagUi) }
        ) {
            Icon(
                modifier = Modifier.size(42.dp),
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.tag_card_edit_button, tagUi.name),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun TagCardContent(
    modifier: Modifier = Modifier,
    bodyContent: String,
){

    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column (
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            text = bodyContent,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = if (isExpanded) 3 else 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        if (!isExpanded && bodyContent.length > 50) {
            Text(
                text = stringResource(R.string.tag_card_read_more_button),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.clickable { isExpanded = !isExpanded }
            )
        }
        if (isExpanded) {
            Text(
                text = stringResource(R.string.tag_card_read_less_button),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.clickable { isExpanded = !isExpanded }
            )
        }
    }
}

@Composable
fun TagCardFoot(
    modifier: Modifier = Modifier,
    tagUi: TagUi,
    onDeleteTag: (TagUi) -> Unit = {},
    onNavigationTo: (TagUi) -> Unit = {}
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier = Modifier,
            onClick = { onDeleteTag(tagUi) }
        ) {
            Icon(
                modifier = Modifier.size(42.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.tag_card_delete_button,tagUi.name),
                tint = MaterialTheme.colorScheme.error
            )
        }
        Spacer(Modifier.weight(1f))
        InsightButton(
            modifier = Modifier,
            text = stringResource(R.string.tag_screen_see_insights_menu),
            iconRes = null,
            onClick = { onNavigationTo(tagUi) }
        )
    }
}

@Composable
fun TagList(
    modifier: Modifier = Modifier,
    tagList: List<TagUi>,
    onEditTagUi: (TagUi) -> Unit,
    onDeleteTagUi: (TagUi) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(tagList) { tagUi ->
            TagCard(
                modifier = modifier.padding(horizontal = 8.dp),
                tagUi = tagUi,
                onEditTagUi = { onEditTagUi(tagUi) },
                onDeleteTagUi = { onDeleteTagUi(tagUi) },
                onNavigationTo = {  }
            )
        }
    }
}

@Preview
@Composable
fun InsightCardPreview() {
    val description = "a".repeat(100)
    TagCard(
        modifier = Modifier.padding(horizontal = 8.dp),
        tagUi = TagUi.fromTag(mockTags[0].copy( description = description))
    )
}