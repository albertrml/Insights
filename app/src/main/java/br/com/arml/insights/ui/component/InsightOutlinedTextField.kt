package br.com.arml.insights.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.insights.R


@Composable
fun InsightOutlinedTextField(
    modifier: Modifier = Modifier,
    nameField: String,
    maxLength: Int,
    maxLines: Int = 1,
    text: String,
    onChangeText: (String) -> Unit = {}
){
    var currentTextSize by rememberSaveable { mutableIntStateOf(text.length) }

    Column (modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            value = text,
            onValueChange = { newText ->
                if (newText.length <= maxLength) {
                    onChangeText(newText)
                    currentTextSize = newText.length
                }
            },
            label = {
                Text(
                    stringResource(id = R.string.outlined_text_field_label, nameField)
                )
            },
            maxLines = maxLines
        )
        Text(
            modifier = Modifier
                .align(Alignment.End),
            text = "$currentTextSize/$maxLength",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun InsightOutlinedTextFieldPreview(){
    InsightOutlinedTextField(
        nameField = "Name",
        text = "Name",
        maxLength = 100,
        onChangeText = {}
    )
}