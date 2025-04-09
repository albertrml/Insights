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
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.insights.ui.component.AnimatedHorizontalDivider
import br.com.arml.insights.ui.theme.Gray200
import br.com.arml.insights.ui.theme.Gray300

@Composable
fun LoadingTagCard(
    modifier: Modifier = Modifier,
){
    OutlinedCard(
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = modifier.padding(vertical = 8.dp, horizontal = 8.dp),
        ){
            LoadingTagCardHeader(modifier = modifier)

            HorizontalDivider(modifier = modifier.padding(vertical = 8.dp), color = Gray200)
            LoadingTagCardContent(modifier = modifier)

            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            AnimatedHorizontalDivider(Modifier.padding(horizontal = 8.dp), 4.dp, Gray300)

            Spacer(modifier = Modifier.padding(vertical = 8.dp))
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
            thickness = 42.dp,
            endColor = Gray300
        )

        Spacer(modifier = Modifier.padding(horizontal = 8.dp))

        AnimatedHorizontalDivider(
            modifier = Modifier.size(42.dp),
            thickness = 42.dp,
            endColor = Gray300
        )

    }
}

@Composable
fun LoadingTagCardContent(
    modifier: Modifier = Modifier,
){
    AnimatedHorizontalDivider(
        modifier = modifier,
        thickness = 42.dp,
        endColor = Gray300
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
            modifier = Modifier.size(42.dp),
            thickness = 42.dp,
            endColor = Gray300
        )
        Spacer(Modifier.weight(1f))
        AnimatedHorizontalDivider(
            modifier = Modifier.width(100.dp),
            thickness = 42.dp,
            endColor = Gray300
        )
    }
}

@Composable
fun LoadingTagCardList(
    modifier: Modifier = Modifier
){
    val list = listOf(1,2,3,4,5,6,7,8,9,10)
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(list){ _ ->
            LoadingTagCard(modifier = Modifier.padding(8.dp))
        }
    }
}

@Preview
@Composable
fun LoadingTagCardsPreview() {
    LoadingTagCardList(modifier = Modifier.padding(8.dp))
}

