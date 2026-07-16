package br.com.arml.insights.ui.component.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.insights.R
import br.com.arml.insights.model.domain.NoteUi
import br.com.arml.insights.model.domain.TagUi
import br.com.arml.insights.model.mock.createSampleNotes
import br.com.arml.insights.model.mock.mockTagEntities
import br.com.arml.insights.ui.component.common.InsightNotePad
import br.com.arml.insights.ui.component.common.InsightTextField
import br.com.arml.insights.ui.theme.dimens

@Composable
fun NoteForms(
    modifier: Modifier = Modifier,
    selectedNote: NoteUi,
    tags: List<TagUi>,
    onEditTitle: (String) -> Unit = {},
    onEditSituation: (String) -> Unit = {},
    onEditBody: (String) -> Unit = {},
    onEditTagId: (Long) -> Unit = {}
) {

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimens.mediumSpacing)
    ) {
        InsightNotePad(
            header = {
                InsightTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimens.smallPadding),
                    nameField = stringResource(R.string.note_forms_title),
                    text = selectedNote.title,
                    onChangeText = onEditTitle,
                    maxSize = 30,
                    textStyle = MaterialTheme.typography.bodyLarge
                )

                InsightTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimens.smallPadding),
                    nameField = stringResource(R.string.note_forms_situation),
                    text = selectedNote.situation,
                    onChangeText = onEditSituation,
                    maxSize = 30,
                    maxLines = 2,
                    textStyle = MaterialTheme.typography.bodyLarge
                )

                InsightDropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimens.smallPadding),
                    selectedNote = selectedNote,
                    tags = tags,
                    onEditTagId = onEditTagId
                )
            },
            modifier = Modifier.padding(dimens.smallPadding),
            noteName = stringResource(R.string.note_forms_insight),
            text = selectedNote.body,
            textStyle = MaterialTheme.typography.bodyLarge,
            onChangeText = onEditBody,
            maxSize = 1000,
            minLines = 12,
            maxLines = 20
        )

        Spacer(modifier = Modifier.size(dimens.smallSpacing))
    }
}

@Composable
fun InsightDropdownMenu(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    selectedNote: NoteUi,
    tags: List<TagUi>,
    onEditTagId: (Long) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.smallSpacing)
        ) {

            TextField(
                modifier = Modifier
                    .weight(1f)
                    .background(color = MaterialTheme.colorScheme.background),
                value = tags.firstOrNull {
                    //** TODO: Note não possui mais tagId. Precisa de ajuste
                    true
                    /*it.id == selectedNote.tagId*/
                }?.name ?: "",
                onValueChange = { },
                label = {
                    Text(
                        text = stringResource(R.string.note_forms_tag_selection),
                        style = textStyle
                    )
                },
                textStyle = textStyle,
                colors = TextFieldDefaults.colors().copy(
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background
                ),
                maxLines = 1
            )
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = stringResource(R.string.note_forms_tag_description)
                )
            }
        }
        DropdownMenu(
            modifier = Modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            tags.forEach { tag ->
                DropdownMenuItem(
                    text = { Text(tag.name) },
                    onClick = {
                        onEditTagId(tag.id)
                        expanded = false
                    }
                )
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun InsightDropdownMenuPreview() {

    var tags by remember {
        mutableStateOf(mockTagEntities.map { TagUi.fromTagEntity(it) }.toList())
    }
    var note by remember { mutableStateOf(createSampleNotes(5).toList().first()) }
    InsightDropdownMenu(
        modifier = Modifier,
        selectedNote = note,
        tags = tags,
        onEditTagId = { tagId -> /*note = note.copy(tagId = tagId)*/ }
    )
}