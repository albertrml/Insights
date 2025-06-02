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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.insights.ui.theme.dimens

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
        modifier = Modifier.border(
            width = MaterialTheme.dimens.smallThickness,
            color = MaterialTheme.colorScheme.onSurface,
            RoundedCornerShape(MaterialTheme.dimens.mediumCornerRadius)
        ),
        elevation = CardDefaults.cardElevation(MaterialTheme.dimens.mediumElevation),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.smallSpacing)
        ) {

            header()

            TextField(
                modifier = modifier
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
                label = {
                    Text(
                        text = noteName,
                        style = textStyle
                    )
                },
                colors = TextFieldDefaults.colors().copy(
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = MaterialTheme.colorScheme.background
                )
            )
            Text(
                modifier = modifier
                    .align(Alignment.End)
                    .padding(
                        end = MaterialTheme.dimens.smallPadding,
                        bottom = MaterialTheme.dimens.smallPadding
                    ),
                text = "${text.length}/${maxSize}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun InsightNotePadPreview(){
    var text by rememberSaveable { mutableStateOf("Texto Inicial") }

    InsightNotePad(
        modifier = Modifier.padding(MaterialTheme.dimens.smallPadding),
        text = text,
        onChangeText = { newText -> text = newText },
        maxSize = 100,
        minLines = 1,
        maxLines = 10
    )
}