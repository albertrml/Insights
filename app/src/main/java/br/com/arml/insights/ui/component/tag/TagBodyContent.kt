package br.com.arml.insights.ui.component.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.model.mock.mockTags

@Composable
fun TagBodyContent(
    modifier: Modifier = Modifier,
    onEditTagUi: (TagUi) -> Unit,
    onDeleteTag: (TagUi) -> Unit
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TagList(
            tagList = mockTags.map { TagUi.fromTag(it) },
            onEditTagUi = onEditTagUi,
            onDeleteTagUi = onDeleteTag
        )
    }
}