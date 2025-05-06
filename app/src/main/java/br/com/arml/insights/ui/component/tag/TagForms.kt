package br.com.arml.insights.ui.component.tag

import androidx.compose.foundation.border
import br.com.arml.insights.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.model.entity.TagUiSaver
import br.com.arml.insights.ui.component.common.InsightColorPicker
import br.com.arml.insights.ui.component.common.InsightButton
import br.com.arml.insights.ui.component.common.InsightOutlinedTextField


@OptIn(ExperimentalStdlibApi::class)
@Composable
fun TagForms(
    modifier: Modifier = Modifier,
    tagUi: TagUi,
    onEditName: (String) -> Unit = {},
    onEditDescription: (String) -> Unit = {},
    onEditColor: (Color) -> Unit = {},
    onClickSave: (tagUi: TagUi) -> Unit = { }
){

    Column(
        modifier=modifier.padding(bottom = 16.dp)
    ) {

        InsightOutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            nameField = stringResource(id = R.string.tag_forms_name_field_label),
            maxSize = 20,
            text = tagUi.name,
            onChangeText = { name -> onEditName(name) }
        )

        InsightOutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            nameField = stringResource(id = R.string.tag_forms_description_field_label),
            maxSize = 150,
            maxLines = 3,
            text = tagUi.description,
            onChangeText = { description -> onEditDescription(description) }
        )

        InsightColorPicker(
            title = stringResource(id = R.string.tag_screen_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.small
                ),
            color = tagUi.color,
            onChangeColor = { color -> onEditColor(color)}
        )

        InsightButton(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            text = stringResource(id = R.string.tag_forms_save_button),
            iconRes = R.drawable.ic_save,
            onClick = {
                onClickSave(tagUi)
            }
        )
    }
}

@Preview
@Composable
fun TagFormPreview(){
    var tagUi by rememberSaveable(stateSaver = TagUiSaver) {
        mutableStateOf(TagUi.fromTag(null))
    }

    TagForms(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        tagUi = tagUi,
        onEditName = { tagUi = tagUi.copy(name = it) },
        onEditDescription = { tagUi = tagUi.copy(description = it) },
        onEditColor = { tagUi = tagUi.copy(color = it) },
    )
}
