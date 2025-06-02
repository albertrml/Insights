package br.com.arml.insights.ui.component.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
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
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.smallSpacing)
    ){

        InsightOutlinedTextField(
            modifier = Modifier.weight(1f),
            nameField = stringResource(R.string.filter_name_label),
            text = searchQuery,
            onChangeText = {
                onSearchTextChange(it)
            },
            maxSize = 20,
        )

        InsightIconButton(
            modifier = Modifier,
            imageVector = ImageVector.vectorResource(id = imageSort),
            onClick = {
                isAscending = !isAscending
                sortedBy(isAscending)
            }
        )

        InsightIconButton(
            modifier = Modifier,
            imageVector = Icons.Filled.Refresh,
            onClick = {
                isAscending = true
                onRefreshTags()
            }
        )

    }

}

@Composable
fun InsightIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit = {}
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        Spacer(modifier = Modifier.padding(vertical = MaterialTheme.dimens.outlinedTextFieldTopPadding))
        IconButton(
            modifier = Modifier
                .size(MaterialTheme.dimens.mediumIcon),
            onClick = onClick,
        ) {
            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.mediumIcon),
                imageVector = imageVector,
                contentDescription = contentDescription
            )
        }

    }
}


@Preview(
    name = "expanded screen",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp")
@Preview(showBackground = true)
@Composable
fun InsightFilterAndSortPreview(){
    InsightFilterAndSort(
        modifier = Modifier,
        sortedBy = {},
        searchQuery = "",
        onSearchTextChange = {},
        onRefreshTags = {}
    )
}