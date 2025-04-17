package br.com.arml.insights.ui.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import br.com.arml.insights.ui.theme.Gray300

@Composable
fun InsightNotePad(
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit = {},
    noteName: String = "",
    text: String,
    onChangeText: (String) -> Unit = {},
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    maxSize: Int,
    minLines: Int,
    maxLines: Int
){
    var currentTextSize by rememberSaveable { mutableIntStateOf(text.length) }

    Card(
        modifier = modifier.border(1.dp, Gray300, RoundedCornerShape(0.dp)),
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            header()

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background),
                value = text,
                onValueChange = { nexText ->
                    if (nexText.length <= maxSize) {
                        currentTextSize = nexText.length
                        onChangeText(nexText)
                    }
                },
                textStyle = textStyle,
                minLines = minLines,
                maxLines = maxLines,
                label = { Text(text = noteName) },
                colors = TextFieldDefaults.colors().copy(
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = MaterialTheme.colorScheme.background
                )
            )
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.dp, bottom = 8.dp),
                text = "${text.length}/${maxSize}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}