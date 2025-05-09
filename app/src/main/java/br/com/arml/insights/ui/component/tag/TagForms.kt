package br.com.arml.insights.ui.component.tag

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.model.entity.TagUiSaver
import br.com.arml.insights.ui.component.common.InsightButton
import br.com.arml.insights.ui.component.common.InsightColorPicker
import br.com.arml.insights.ui.component.common.InsightOutlinedTextField
import br.com.arml.insights.ui.theme.dimens

@Composable
fun TagForms(
    modifier: Modifier = Modifier,
    tagUi: TagUi,
    onEditName: (String) -> Unit = {},
    onEditDescription: (String) -> Unit = {},
    onEditColor: (Color) -> Unit = {},
    onClickSave: (tagUi: TagUi) -> Unit = {},
){

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TagFields(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.small),
            name = tagUi.name,
            description = tagUi.description,
            onNameChange = onEditName,
            onDescriptionChange = onEditDescription
        )
        TagColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.small),
            selectedColor = tagUi.color,
            onColorChange = onEditColor
        )
        TagSaveButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.small),
            onClickSave = { onClickSave(tagUi) }
        )
    }

}

@Composable
fun TagFields(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit
){

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small),
        horizontalAlignment = Alignment.Start
    ) {
        InsightOutlinedTextField(
            nameField = stringResource(id = R.string.tag_forms_name_field_label),
            text = name,
            onChangeText = onNameChange,
            maxSize = 30
        )
        InsightOutlinedTextField(
            nameField = stringResource(id = R.string.tag_forms_description_field_label),
            text = description,
            onChangeText = onDescriptionChange,
            maxSize = 30
        )
    }

}

@Composable
fun TagColorPicker(
    modifier: Modifier = Modifier,
    selectedColor: Color,
    onColorChange: (Color) -> Unit
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        InsightColorPicker(
            modifier = Modifier
                .border(
                    width = MaterialTheme.dimens.extraSmallThickness,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.small
                )
                .padding(MaterialTheme.dimens.medium),
            title = "Tag",
            color = selectedColor,
            onChangeColor = onColorChange
        )
    }
}

@Composable
fun TagSaveButton(
    modifier: Modifier = Modifier,
    onClickSave: () -> Unit = {}
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        InsightButton(
            text = "Save",
            onClick = onClickSave,
            iconRes = R.drawable.ic_save
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TagFormPreview(){
    var tagUi by rememberSaveable(stateSaver = TagUiSaver) {
        mutableStateOf(TagUi.fromTag(null))
    }

    TagForms(
        modifier = Modifier.padding(MaterialTheme.dimens.medium),
        tagUi = tagUi,
        onEditName = { tagUi = tagUi.copy(name = it) },
        onEditDescription = { tagUi = tagUi.copy(description = it) },
        onEditColor = { tagUi = tagUi.copy(color = it) },
    )
}
