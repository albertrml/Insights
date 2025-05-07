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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.ui.component.common.AnimatedHorizontalDivider
import br.com.arml.insights.ui.component.common.InsightButton
import br.com.arml.insights.ui.theme.dimens

@Composable
fun TagCard(
    modifier: Modifier = Modifier,
    tagUi: TagUi,
    onEditTagUi: (TagUi) -> Unit = {},
    onDeleteTagUi: (TagUi) -> Unit = {},
    onNavigationTo: (TagUi) -> Unit = {}
){
    OutlinedCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(MaterialTheme.dimens.cardElevation)
    ) {
        Column(
            modifier = Modifier.padding(MaterialTheme.dimens.medium),
        ){
            TagCardHeader(modifier = Modifier, tagUi = tagUi, onEditTag = onEditTagUi)
            Spacer(modifier = Modifier.padding(MaterialTheme.dimens.small))
            HorizontalDivider(
                modifier = Modifier,
                color = MaterialTheme.colorScheme.onSurface,
                thickness = MaterialTheme.dimens.smallThickness
            )
            Spacer(modifier = Modifier.padding(MaterialTheme.dimens.small))
            TagCardContent(modifier = modifier, bodyContent = tagUi.description)
            Spacer(modifier = Modifier.padding(MaterialTheme.dimens.small))
            AnimatedHorizontalDivider(
                modifier = Modifier,
                thickness = MaterialTheme.dimens.smallThickness,
                endColor = tagUi.color
            )
            Spacer(modifier = Modifier.padding(MaterialTheme.dimens.small))
            TagCardFoot(
                modifier = Modifier,
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
    textStyle: TextStyle = MaterialTheme.typography.titleLarge
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = tagUi.name,
            style = textStyle
        )
        IconButton(
            onClick = { onEditTag(tagUi) }
        ) {
            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.icon),
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
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
){

    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column (
        modifier = modifier,
    ) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            text = bodyContent,
            style = textStyle,
            maxLines = if (isExpanded) 3 else 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.padding(vertical = MaterialTheme.dimens.small))

        if (!isExpanded && bodyContent.length > 50) {
            Text(
                text = stringResource(R.string.tag_card_read_more_button),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.small)
                    .clickable { isExpanded = !isExpanded }
            )
        }
        if (isExpanded) {
            Text(
                text = stringResource(R.string.tag_card_read_less_button),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.small)
                    .clickable { isExpanded = !isExpanded }
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
                modifier = Modifier.size(MaterialTheme.dimens.icon),
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.tag_card_delete_button,tagUi.name),
                tint = MaterialTheme.colorScheme.error
            )
        }
        Spacer(Modifier.weight(1f))
        InsightButton(
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
    onDeleteTagUi: (TagUi) -> Unit = {},
    onNavigationTo: (TagUi) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium)
    ){
        items(tagList) { tagUi ->
            TagCard(
                modifier = modifier,
                tagUi = tagUi,
                onEditTagUi = { onEditTagUi(tagUi) },
                onDeleteTagUi = { onDeleteTagUi(tagUi) },
                onNavigationTo = onNavigationTo
            )
        }
    }
}

@Preview
@Composable
fun InsightCardPreview() {
    val description = "a".repeat(100)
    TagCard(
        modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small),
        tagUi = TagUi.fromTag(mockTags[0].copy( description = description))
    )
}