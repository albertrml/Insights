package br.com.arml.insights.ui.component.common

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun InsightOutlinedTextField(
    modifier: Modifier = Modifier,
    nameField: String,
    text: String,
    onChangeText: (String) -> Unit = {},
    maxSize: Int,
    maxLines: Int = 1
){
    var currentTextSize by rememberSaveable { mutableIntStateOf(text.length) }

    Column (modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            value = text,
            onValueChange = { newText ->
                if (newText.length <= maxSize) {
                    onChangeText(newText)
                    currentTextSize = newText.length
                }
            },
            label = { Text( text = nameField) },
            maxLines = maxLines
        )
        Text(
            modifier = Modifier
                .align(Alignment.End),
            text = "$currentTextSize/$maxSize",
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
        maxSize = 100,
        onChangeText = {}
    )
}