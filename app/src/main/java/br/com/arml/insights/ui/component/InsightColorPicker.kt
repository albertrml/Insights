package br.com.arml.insights.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.TagUi
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun InsightColorPicker(
    modifier: Modifier = Modifier,
    color: Color,
    onChangeColor: (Color) -> Unit
){
    val controller = rememberColorPickerController()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    )
    {
        Text(
            modifier = Modifier.padding(12.dp).align(alignment = Alignment.CenterHorizontally),
            text = stringResource(id = R.string.colorpicker_title_label),
            style = MaterialTheme.typography.bodyLarge
        )
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(12.dp),
            controller = controller,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                onChangeColor(colorEnvelope.color)
            },
            initialColor = color,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
                .align(alignment = Alignment.CenterHorizontally),
            text = stringResource(id = R.string.colorpicker_alpha_label),
            style = MaterialTheme.typography.bodyLarge
        )

        AlphaSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp, vertical = 6.dp)
                .height(35.dp),
            borderRadius = 6.dp,
            borderSize = 5.dp,
            wheelRadius = 18.dp,
            wheelColor = Color.Gray,
            borderColor = Color.LightGray,
            controller = controller,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
                .align(alignment = Alignment.CenterHorizontally),
            text = stringResource(id = R.string.colorpicker_brightness_label),
            style = MaterialTheme.typography.bodyLarge
        )

        BrightnessSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp, vertical = 6.dp)
                .height(35.dp),
            borderRadius = 6.dp,
            borderSize = 5.dp,
            wheelRadius = 18.dp,
            wheelColor = Color.Gray,
            borderColor = Color.LightGray,
            controller = controller,
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = stringResource(id = R.string.colorpicker_selected_color_label),
                style = MaterialTheme.typography.bodyLarge
            )
            Box(
                modifier = Modifier
                    .background(color = color)
                    .size(40.dp)
            )
        }
    }
}

@Preview
@Composable
fun ColorPickerPreview(){
    val colorSaver: Saver<Color, *> = Saver(
        save = { color -> color.toArgb().toLong() },
        restore = { value -> Color(value) }
    )

    var tagUi by rememberSaveable(stateSaver = colorSaver) { mutableStateOf(TagUi.fromTag(null).color) }
    InsightColorPicker(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.small
            ),
        color = tagUi,
        onChangeColor = { color -> tagUi = color}
    )
}