package br.com.arml.insights.ui.component.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.insights.R
import br.com.arml.insights.ui.theme.dimens

@Composable
fun InsightButton(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    @DrawableRes iconRes: Int? = null,
    onClick: () -> Unit,
    text: String? = null
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(MaterialTheme.dimens.largeCornerRadius),
        contentPadding = contentPaddingButton(text, iconRes),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.mediumSpacing)
        ) {
            iconRes?.let {
                Icon(
                    modifier = Modifier.size(MaterialTheme.dimens.smallIcon),
                    painter = painterResource(id = iconRes),
                    contentDescription = stringResource(R.string.button_description, text?:"")
                )
            }
            text?.let {
                Text(
                    text = text,
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
    { PaddingValues(0.dp) } else { ButtonDefaults.ContentPadding }

@Preview
@Composable
fun InsightButtonPreview(){
    InsightButton(
        text = "Example",
        iconRes = R.drawable.ic_note,
        onClick = {}
    )
}