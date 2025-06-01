package br.com.arml.insights.ui.component.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.ui.screen.tag.TagOperation
import br.com.arml.insights.ui.theme.Green500
import br.com.arml.insights.ui.theme.dimens

@Composable
fun TagSheetContent(
    modifier: Modifier = Modifier,
    selectedTagUi: TagUi,
    selectedOperation: TagOperation = TagOperation.None,
    onEditName: (String) -> Unit = {},
    onEditDescription: (String) -> Unit = {},
    onEditColor: (Color) -> Unit = {},
    onClickClose: () -> Unit = {},
    onClickSave: (TagUi) -> Unit = {}
){

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium),
    ) {
        TagSheetHeader(
            modifier = Modifier.fillMaxWidth(),
            operation = selectedOperation,
            onClickClose = onClickClose,
            onClickSave = { onClickSave(selectedTagUi) }
        )

        HorizontalDivider(
            modifier = Modifier,
            thickness = MaterialTheme.dimens.smallThickness
        )

        TagForms(
            modifier = Modifier.fillMaxSize(),
            tagUi = selectedTagUi,
            onEditName = onEditName,
            onEditDescription = onEditDescription,
            onEditColor = onEditColor
        )
    }
}

@Composable
fun TagSheetHeader(
    modifier: Modifier = Modifier,
    operation: TagOperation,
    onClickClose: () -> Unit,
    onClickSave: () -> Unit
){

    val title = getTagHeaderTitle(operation)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small),
        verticalAlignment = Alignment.CenterVertically
    )
    {

        Icon(
            modifier = Modifier.size(MaterialTheme.dimens.icon),
            imageVector = ImageVector.vectorResource(R.drawable.ic_tag),
            contentDescription = stringResource(R.string.tag_screen_title),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.headlineLarge
        )

        IconButton(
            onClick = onClickSave
        ) {

            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.icon),
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(
                    id = R.string.tag_forms_save_button,title
                ),
                tint = Green500
            )
        }

        IconButton(
            onClick = onClickClose
        ) {

            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.icon),
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(
                    id = R.string.tag_screen_close_menu,title
                ),
                tint = Color.Red
            )
        }
    }
}

@Composable
fun getTagHeaderTitle(
    operation: TagOperation
): String{
    return when(operation){
        TagOperation.OnInsert -> stringResource(R.string.tag_operation_insert)
        TagOperation.OnUpdate -> stringResource(R.string.tag_operation_edit)
        TagOperation.None -> ""
    }
}

@Preview(
    name = "compact screen",
    showBackground = true,
    device = "spec:width=360dp,height=640dp")
@Preview(
    name = "medium screen",
    showBackground = true,
    device = "spec:width=412dp,height=924dp")
@Preview(
    name = "expanded screen",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp")
@Composable
fun TagSheetContentPreview(){
    TagSheetContent(
        modifier = Modifier.padding(MaterialTheme.dimens.small),
        selectedTagUi = TagUi.fromTag(null)
    )
}