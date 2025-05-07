package br.com.arml.insights.ui.component.tag

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    val lazyListState = rememberLazyListState()
    LaunchedEffect(tagUi.id) {
        lazyListState.scrollToItem(0)
    }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium)
    ) {
        InsightOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            nameField = stringResource(id = R.string.tag_forms_name_field_label),
            maxSize = 20,
            text = tagUi.name,
            onChangeText = { name -> onEditName(name) }
        )

        InsightOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            nameField = stringResource(id = R.string.tag_forms_description_field_label),
            maxSize = 150,
            maxLines = 3,
            text = tagUi.description,
            onChangeText = { description -> onEditDescription(description) }
        )

        InsightColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = MaterialTheme.dimens.extraSmallThickness,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.small
                )
                .padding(MaterialTheme.dimens.medium),
            title = stringResource(id = R.string.tag_screen_title),
            color = tagUi.color,
            onChangeColor = { color -> onEditColor(color)}
        )

        InsightButton(
            modifier = Modifier,
            text = stringResource(id = R.string.tag_forms_save_button),
            iconRes = R.drawable.ic_save,
            onClick = {
                onClickSave(tagUi)
            }
        )

        Spacer(modifier = Modifier.padding(MaterialTheme.dimens.small))
    }

    /*LazyColumn(
        modifier = modifier,
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium)
    ) {
        item{
            InsightOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                nameField = stringResource(id = R.string.tag_forms_name_field_label),
                maxSize = 20,
                text = tagUi.name,
                onChangeText = { name -> onEditName(name) }
            )

            InsightOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                nameField = stringResource(id = R.string.tag_forms_description_field_label),
                maxSize = 150,
                maxLines = 3,
                text = tagUi.description,
                onChangeText = { description -> onEditDescription(description) }
            )

            InsightColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = MaterialTheme.dimens.extraSmallThickness,
                        color = MaterialTheme.colorScheme.outline,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(MaterialTheme.dimens.medium),
                title = stringResource(id = R.string.tag_screen_title),
                color = tagUi.color,
                onChangeColor = { color -> onEditColor(color)}
            )

            InsightButton(
                modifier = Modifier,
                text = stringResource(id = R.string.tag_forms_save_button),
                iconRes = R.drawable.ic_save,
                onClick = {
                    onClickSave(tagUi)
                }
            )
        }
    }*/
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
