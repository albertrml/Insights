package br.com.arml.insights.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.arml.insights.R

@Composable
fun InsightButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    @DrawableRes iconRes: Int? = null,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Button(
        modifier = modifier.heightIn(min=56.dp),
        shape = RoundedCornerShape(16.dp),
        contentPadding = contentPaddingButton(text, iconRes),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            iconRes?.let {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = stringResource(R.string.button_description, text?:"")
                )
            }
            text?.let {
                Text(
                    text = text.uppercase(),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

fun contentPaddingButton(
    text: String?,
    iconRes: Int?
): PaddingValues = if ( text == null && iconRes != null )
{
    PaddingValues(0.dp)
} else {
    ButtonDefaults.ContentPadding
}