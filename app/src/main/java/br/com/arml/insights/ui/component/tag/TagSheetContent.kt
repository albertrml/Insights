package br.com.arml.insights.ui.component.tag

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.ui.component.common.InsightHeaderForms
import br.com.arml.insights.ui.theme.dimens

@Composable
fun TagSheetContent(
    modifier: Modifier = Modifier,
    selectedTagUi: TagUi,
    onEditName: (String) -> Unit = {},
    onEditDescription: (String) -> Unit = {},
    onEditColor: (Color) -> Unit = {},
    onClickClose: () -> Unit = {},
    onClickSave: (TagUi) -> Unit = {}
){

    Column (
        modifier = modifier
    ) {

        InsightHeaderForms(
            modifier = Modifier.fillMaxWidth(),
            title = selectedTagUi.name,
            onClickClose = onClickClose
        )
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.small))
        HorizontalDivider(
            modifier = Modifier,
            thickness = MaterialTheme.dimens.smallThickness
        )
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.small))
        TagForms(
            modifier = modifier,
            tagUi = selectedTagUi,
            onEditName = onEditName,
            onEditDescription = onEditDescription,
            onEditColor = onEditColor,
            onClickSave = { onClickSave(it) }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun TagSheetContentPreview(){
    TagSheetContent(
        modifier = Modifier.padding(MaterialTheme.dimens.small),
        selectedTagUi = TagUi.fromTag(null)
    )
}