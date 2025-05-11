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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.model.mock.createSampleNotes
import br.com.arml.insights.model.mock.mockTags
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
    onEditTagId: (Int) -> Unit = {}
) {

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium)
    ) {
        InsightNotePad(
            header = {
                InsightTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.small),
                    nameField = "Title",
                    text = selectedNote.title,
                    onChangeText = onEditTitle,
                    maxSize = 30,
                    textStyle = MaterialTheme.typography.bodyLarge
                )

                InsightTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.small),
                    nameField = "Situation",
                    text = selectedNote.situation,
                    onChangeText = onEditSituation,
                    maxSize = 30,
                    maxLines = 2,
                    textStyle = MaterialTheme.typography.bodyLarge
                )

                InsightDropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.small),
                    selectedNote = selectedNote,
                    tags = tags,
                    onEditTagId = onEditTagId
                )
            },
            modifier = Modifier.padding(MaterialTheme.dimens.small),
            noteName = "What is your insight?",
            text = selectedNote.body,
            textStyle = MaterialTheme.typography.bodyLarge,
            onChangeText = onEditBody,
            maxSize = 1000,
            minLines = 12,
            maxLines = 20
        )

        Spacer(modifier = Modifier.size(MaterialTheme.dimens.small))
    }
}

@Composable
fun InsightDropdownMenu(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    selectedNote: NoteUi,
    tags: List<TagUi>,
    onEditTagId: (Int) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier,
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small)
        ) {

            TextField(
                modifier = Modifier
                    .weight(1f)
                    .background(color = MaterialTheme.colorScheme.background),
                value = tags.firstOrNull { it.id == selectedNote.tagId }?.name ?: "",
                onValueChange = { },
                label = {
                    Text(
                        text = "Tag",
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
                Icon(Icons.Default.ArrowDropDown, contentDescription = "More options")
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

    var tags by remember { mutableStateOf(mockTags.map { TagUi.fromTag(it) }.toList()) }
    var note by remember { mutableStateOf(createSampleNotes().toList().first()) }
    InsightDropdownMenu(
        modifier = Modifier,
        selectedNote = note,
        tags = tags,
        onEditTagId = { tagId -> note = note.copy(tagId = tagId) }
    )
}