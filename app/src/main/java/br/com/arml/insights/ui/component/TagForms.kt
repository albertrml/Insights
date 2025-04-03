package br.com.arml.insights.ui.component

import br.com.arml.insights.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.mock.mockTags
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController


@OptIn(ExperimentalStdlibApi::class)
@Composable
fun TagForms(
    modifier: Modifier = Modifier,
    tag: Tag?,
    onClickSave: (Int, String, String, Long) -> Unit = { _, _, _, _ -> }
){

    var name by rememberSaveable { mutableStateOf(tag?.name ?: "") }
    var description by rememberSaveable { mutableStateOf(tag?.description ?: "" ) }
    var color by rememberSaveable { mutableLongStateOf(tag?.color ?: 0xFF444444) }
    val controller = rememberColorPickerController()

    Column(
        modifier=modifier
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )

        Row (
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = "Pick a Color",
                style = MaterialTheme.typography.bodyLarge
            )
            Box(
                modifier = Modifier
                    .background(color = Color(color))
                    .size(40.dp)
                    .clickable{ /* TODO */ }
            )
        }

        /*Text(text = color.toHexString(), modifier = Modifier.fillMaxWidth().padding(12.dp))*/

        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(10.dp),
            controller = controller,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                color = colorEnvelope.color.value.toLong()
            },
            initialColor = Color(color),
        )

        InsightButton(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            text = "Save",
            iconRes = R.drawable.ic_save,
            onClick = {
                onClickSave(
                    tag?.id ?: 0,
                    name,
                    description,
                    color
                )
            }
        )
    }
}

@Preview
@Composable
fun TagFormsPreview(){

    TagForms(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        tag = mockTags.first()
    )

}