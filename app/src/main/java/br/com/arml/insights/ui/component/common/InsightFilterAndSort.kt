package br.com.arml.insights.ui.component.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.insights.R
import br.com.arml.insights.ui.theme.dimens

@Composable
fun InsightFilterAndSort(
    modifier: Modifier = Modifier,
    sortedBy: (Boolean) -> Unit = {},
    searchQuery: String,
    onSearchTextChange: (String) -> Unit = {},
    onRefreshTags: () -> Unit = {}
){
    var isAscending by rememberSaveable { mutableStateOf(true) }
    var imageSort = if (isAscending)
        R.drawable.ic_ascending
    else
        R.drawable.ic_descending

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium)
    ){

        InsightOutlinedTextField(
            modifier = Modifier.weight(1f),
            nameField = "Filter",
            text = searchQuery,
            onChangeText = {
                onSearchTextChange(it)
            },
            maxSize = 20,
        )

        IconButton(
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.medium)
                .size(MaterialTheme.dimens.icon)
                .clip(RoundedCornerShape(MaterialTheme.dimens.mediumCornerRadius)),
            onClick = {
                isAscending = !isAscending
                sortedBy(isAscending)
            },
        ) {
            Icon(
                modifier = Modifier.padding(MaterialTheme.dimens.small),
                imageVector = ImageVector.vectorResource(id = imageSort),
                contentDescription = null
            )
        }

        IconButton(
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.medium)
                .size(MaterialTheme.dimens.icon)
                .clip(RoundedCornerShape(MaterialTheme.dimens.mediumCornerRadius)),
            onClick = {
                isAscending = true
                onRefreshTags()
            },
        ) {
            Icon(
                modifier = Modifier,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_refresh),
                contentDescription = null
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun InsightFilterAndSortPreview(){
    InsightFilterAndSort(
        modifier = Modifier.padding(horizontal = MaterialTheme.dimens.medium),
        sortedBy = {},
        searchQuery = "",
        onSearchTextChange = {},
        onRefreshTags = {}
    )
}