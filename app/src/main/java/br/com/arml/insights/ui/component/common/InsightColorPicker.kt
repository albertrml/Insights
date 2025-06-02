package br.com.arml.insights.ui.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.insights.R
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.ui.theme.dimens
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun InsightColorPicker(
    modifier: Modifier = Modifier,
    color: Color,
    onChangeColor: (Color) -> Unit,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.headlineSmall
){
    val controller = rememberColorPickerController()

    Column(
        modifier = modifier.padding(MaterialTheme.dimens.mediumPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    )
    {
        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            text = stringResource(id = R.string.colorpicker_title_label,title),
            style = titleStyle
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = MaterialTheme.dimens.largePadding),
            thickness = MaterialTheme.dimens.smallThickness
        )

        Row(
            modifier = Modifier.height(MaterialTheme.dimens.colorPickerHeight),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            HsvColorPicker(
                modifier = Modifier.weight(1f),
                controller = controller,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    onChangeColor(colorEnvelope.color)
                },
                initialColor = color,
            )

            Spacer(modifier = Modifier.padding(MaterialTheme.dimens.smallSpacing))

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.colorpicker_selected_color_label),
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.padding(MaterialTheme.dimens.smallSpacing))
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.dimens.smallIcon)
                        .border(
                            width = MaterialTheme.dimens.smallThickness,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = MaterialTheme.shapes.small
                        )
                        .clip(shape = RoundedCornerShape(MaterialTheme.dimens.mediumCornerRadius))
                        .background(color = color),
                )
            }

        }

        Spacer(modifier = Modifier.padding(MaterialTheme.dimens.mediumSpacing))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally),
            text = stringResource(id = R.string.colorpicker_alpha_label),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.padding(MaterialTheme.dimens.smallSpacing))

        AlphaSlider(
            modifier = Modifier
                .height(MaterialTheme.dimens.sliderHeight),
            borderRadius = MaterialTheme.dimens.mediumThickness,
            borderSize = MaterialTheme.dimens.mediumThickness,
            wheelRadius = MaterialTheme.dimens.sliderWheelRadius,
            wheelColor = MaterialTheme.colorScheme.onSurface,
            borderColor = MaterialTheme.colorScheme.onSurface,
            controller = controller,
        )

        Spacer(modifier = Modifier.padding(MaterialTheme.dimens.mediumSpacing))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally),
            text = stringResource(id = R.string.colorpicker_brightness_label),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.padding(MaterialTheme.dimens.smallSpacing))

        BrightnessSlider(
            modifier = Modifier.height(MaterialTheme.dimens.sliderHeight),
            borderRadius = MaterialTheme.dimens.mediumThickness,
            borderSize = MaterialTheme.dimens.mediumThickness,
            wheelRadius = MaterialTheme.dimens.sliderWheelRadius,
            wheelColor = MaterialTheme.colorScheme.onSurface,
            borderColor = MaterialTheme.colorScheme.onSurface,
            controller = controller,
        )

        Spacer(modifier = Modifier.padding(MaterialTheme.dimens.smallSpacing))
    }
}

@Preview(
    name = "expanded screen",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp")
@Composable
fun ColorPickerPreview(){
    val colorSaver: Saver<Color, *> = Saver(
        save = { color -> color.toArgb().toLong() },
        restore = { value -> Color(value) }
    )

    var tagUi by rememberSaveable(stateSaver = colorSaver) { mutableStateOf(TagUi.fromTag(null).color) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        InsightColorPicker(
            title = "Tag",
            modifier = Modifier
                .border(
                    width = MaterialTheme.dimens.smallThickness,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.small
                )
                .padding(MaterialTheme.dimens.mediumSpacing),
            color = tagUi,
            onChangeColor = { color -> tagUi = color}
        )
    }
}