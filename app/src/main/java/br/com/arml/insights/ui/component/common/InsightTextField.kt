package br.com.arml.insights.ui.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun InsightTextField(
    modifier: Modifier = Modifier,
    nameField: String,
    text: String,
    onChangeText: (String) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    maxSize: Int,
    maxLines: Int = 1,
){

    var currentTextSize by rememberSaveable { mutableIntStateOf(text.length) }

    Column(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .background(color = MaterialTheme.colorScheme.background),
            value = text,
            onValueChange = { newText ->
                if (newText.length <= maxSize) {
                    onChangeText(newText)
                    currentTextSize = newText.length
                }
            },
            label = { Text(text = nameField) },
            textStyle = textStyle,
            colors = TextFieldDefaults.colors().copy(
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background
            ),
            maxLines = maxLines
        )
        Text(
            modifier = Modifier
                .align(Alignment.End)
                .padding(6.dp),
            text = "$currentTextSize/$maxSize",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}