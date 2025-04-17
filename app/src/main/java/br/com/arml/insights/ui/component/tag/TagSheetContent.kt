package br.com.arml.insights.ui.component.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.ui.theme.Gray300

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
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        Row(
            modifier = modifier
        )
        {
            Text(
                modifier = Modifier.weight(1f),
                text = selectedTagUi.name,
                style = MaterialTheme.typography.headlineLarge
            )
            IconButton(
                onClick = onClickClose
            ) {
                Icon(
                    modifier = Modifier.size(42.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(
                        id = R.string.tag_screen_close_menu,
                        selectedTagUi.name
                    ),
                    tint = Color.Black
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier,
            thickness = 2.dp,
            color = Gray300
        )

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