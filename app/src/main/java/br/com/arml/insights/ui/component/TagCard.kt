package br.com.arml.insights.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.ui.theme.Gray200

@Composable
fun TagCard(
    modifier: Modifier = Modifier,
    tag: Tag,
    onEditTag: (Tag) -> Unit = {},
    onDeleteTag: (Tag) -> Unit = {},
    onNavigationTo: (Tag) -> Unit = {}
){
    OutlinedCard(
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = modifier.padding(vertical = 8.dp, horizontal = 8.dp),
        ){
            TagCardHeader(modifier = modifier, tag = tag, onEditTag = onEditTag)

            HorizontalDivider(modifier = modifier.padding(vertical = 8.dp), color = Gray200)
            TagCardContent(modifier = modifier, bodyContent = tag.description)

            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            AnimatedHorizontalDivider(Modifier.padding(horizontal = 8.dp), 4.dp, Color(tag.color))

            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            TagCardFoot(
                modifier = modifier,
                tag = tag,
                onDeleteTag = onDeleteTag,
                onNavigationTo = onNavigationTo
            )
        }
    }
}

@Composable
fun TagCardHeader(
    modifier: Modifier = Modifier,
    tag: Tag,
    onEditTag: (Tag) -> Unit = {},
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = tag.name,
            style = MaterialTheme.typography.titleLarge
        )
        IconButton(
            onClick = { onEditTag(tag) }
        ) {
            Icon(
                modifier = Modifier.size(42.dp),
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit ${tag.name}",
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
                text = "Read more",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.clickable { isExpanded = !isExpanded }
            )
        }
        if (isExpanded) {
            Text(
                text = "Read less",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.clickable { isExpanded = !isExpanded }
            )
        }
    }
}

@Composable
fun TagCardFoot(
    modifier: Modifier = Modifier,
    tag: Tag,
    onDeleteTag: (Tag) -> Unit = {},
    onNavigationTo: (Tag) -> Unit
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier = Modifier,
            onClick = { onDeleteTag(tag) }
        ) {
            Icon(
                modifier = Modifier.size(42.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete ${tag.name}",
                tint = MaterialTheme.colorScheme.error
            )
        }
        Spacer(Modifier.weight(1f))
        InsightButton (
            modifier = Modifier,
            text = "See Insights",
            iconRes = null,
            onClick = { onNavigationTo(tag) }
        )
    }
}

@Preview
@Composable
fun InsightCardPreview() {
    val description = "a".repeat(100)
    TagCard(
        modifier = Modifier.padding(horizontal = 8.dp),
        tag = mockTags[0].copy( description = description)
    )
}