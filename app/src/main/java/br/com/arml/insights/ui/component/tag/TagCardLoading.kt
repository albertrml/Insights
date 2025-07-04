package br.com.arml.insights.ui.component.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.insights.ui.component.common.AnimatedHorizontalDivider
import br.com.arml.insights.ui.theme.dimens
import br.com.arml.insights.ui.theme.onSurfaceLight

@Composable
fun TagCardLoading(
    modifier: Modifier = Modifier,
){
    OutlinedCard(
        elevation = CardDefaults.cardElevation(MaterialTheme.dimens.mediumElevation)
    ) {
        Column(
            modifier = modifier.padding(MaterialTheme.dimens.smallPadding),
        ){
            LoadingTagCardHeader(modifier = modifier)

            HorizontalDivider(
                modifier = modifier.padding(vertical = MaterialTheme.dimens.smallPadding),
                color = onSurfaceLight
            )
            LoadingTagCardContent(modifier = modifier)

            Spacer(modifier = Modifier.padding(vertical = MaterialTheme.dimens.smallSpacing))
            AnimatedHorizontalDivider(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.smallPadding),
                thickness = MaterialTheme.dimens.mediumThickness,
                endColor = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.padding(vertical = MaterialTheme.dimens.smallSpacing))
            LoadingTagCardFoot(modifier = modifier)
        }
    }
}

@Composable
fun LoadingTagCardHeader(
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AnimatedHorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = MaterialTheme.dimens.smallIcon,
            endColor = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.padding(horizontal = MaterialTheme.dimens.smallSpacing))

        AnimatedHorizontalDivider(
            modifier = Modifier.size(MaterialTheme.dimens.smallIcon),
            thickness = MaterialTheme.dimens.smallIcon,
            endColor = MaterialTheme.colorScheme.outline
        )

    }
}

@Composable
fun LoadingTagCardContent(
    modifier: Modifier = Modifier,
){
    AnimatedHorizontalDivider(
        modifier = modifier,
        thickness = MaterialTheme.dimens.smallIcon,
        endColor = MaterialTheme.colorScheme.outline
    )
}

@Composable
fun LoadingTagCardFoot(
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnimatedHorizontalDivider(
            modifier = Modifier.size(MaterialTheme.dimens.smallIcon),
            thickness = MaterialTheme.dimens.smallIcon,
            endColor = MaterialTheme.colorScheme.outline
        )
        Spacer(Modifier.weight(1f))
        AnimatedHorizontalDivider(
            modifier = Modifier.width(MaterialTheme.dimens.largeIcon),
            thickness = MaterialTheme.dimens.smallIcon,
            endColor = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
fun TagCardLoadingList(
    modifier: Modifier = Modifier
){
    val list = listOf(1,2,3,4,5,6,7,8,9,10)
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.mediumPadding)
    ) {
        items(list){ _ ->
            TagCardLoading(modifier = Modifier.padding(MaterialTheme.dimens.smallSpacing))
        }
    }
}

@Preview
@Composable
fun LoadingTagCardsPreview() {
    TagCardLoadingList(modifier = Modifier.padding(MaterialTheme.dimens.smallSpacing))
}

