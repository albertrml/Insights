package br.com.arml.insights.ui.component.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.data.ShowResults

@Composable
fun TagBodyContent(
    modifier: Modifier = Modifier,
    tags: Response<List<TagUi>>,
    onEditTagUi: (TagUi) -> Unit,
    onDeleteTag: (TagUi) -> Unit,
    onNavigationTo: (TagUi) -> Unit
){
    tags.ShowResults(
        successContent = {
            RetrievedTags(
                modifier = modifier,
                tagList = it,
                onEditTagUi = onEditTagUi,
                onDeleteTagUi = onDeleteTag,
                onNavigationTo = onNavigationTo
            )
        },
        loadingContent = { TagCardLoadingList(modifier = modifier) },
        failureContent = { }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetrievedTags(
    modifier: Modifier = Modifier,
    tagList: List<TagUi>,
    onEditTagUi: (TagUi) -> Unit,
    onDeleteTagUi: (TagUi) -> Unit,
    onNavigationTo: (TagUi) -> Unit = {}
){

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TagList(
            modifier = Modifier,
            tagList = tagList,
            onEditTagUi = onEditTagUi,
            onDeleteTagUi = onDeleteTagUi,
            onNavigationTo = onNavigationTo
        )
    }
}

@Preview (showBackground = true)
@Composable
fun TagBodyContentPreview(){
    val tags by remember { mutableStateOf(mockTags.map { TagUi.fromTag(it) }) }
    RetrievedTags(
        tagList = tags,
        onEditTagUi = {},
        onDeleteTagUi = {},
    )
}
