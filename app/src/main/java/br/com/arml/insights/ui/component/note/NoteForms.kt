package br.com.arml.insights.ui.component.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.ui.component.common.InsightButton
import br.com.arml.insights.ui.component.common.InsightNotePad
import br.com.arml.insights.ui.component.common.InsightTextField
import br.com.arml.insights.ui.theme.dimens

@Composable
fun NoteForms(
    modifier: Modifier = Modifier,
    selectedNote: NoteUi,
    onEditTitle: (String) -> Unit = {},
    onEditSituation: (String) -> Unit = {},
    onEditBody: (String) -> Unit = {},
    onClickSave: (NoteUi) -> Unit = {}
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
            },
            modifier = Modifier.padding(MaterialTheme.dimens.small),
            noteName = "What is your insight?",
            text = selectedNote.body,
            textStyle = MaterialTheme.typography.bodyLarge,
            onChangeText = onEditBody,
            maxSize = 1000,
            minLines = 10,
            maxLines = 20
        )

        InsightButton(
            text = stringResource(R.string.note_forms_save_button),
            onClick = { onClickSave(selectedNote) },
            iconRes = R.drawable.ic_save
        )

        Spacer(modifier = Modifier.size(MaterialTheme.dimens.small))
    }
}