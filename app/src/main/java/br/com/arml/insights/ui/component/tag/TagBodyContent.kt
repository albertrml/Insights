package br.com.arml.insights.ui.component.tag

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.arml.core.response.Response
import br.com.arml.core.response.ui.ShowResults
import br.com.arml.insights.model.domain.TagUi

@Composable
fun TagBodyContent(
    modifier: Modifier = Modifier,
    tags: Response<List<TagUi>>,
    onEditTagUi: (TagUi) -> Unit,
    onDeleteTagUi: (TagUi) -> Unit,
    onNavigationTo: (TagUi) -> Unit
){
    tags.ShowResults(
        successContent = {
            TagList(
                modifier = modifier,
                tagList = it,
                onEditTagUi = onEditTagUi,
                onDeleteTagUi = onDeleteTagUi,
                onNavigationTo = onNavigationTo
            )
        }
    )
}
