package br.com.arml.insights.ui.component.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.arml.insights.R
import br.com.arml.insights.ui.theme.dimens

@Composable
fun InsightHeaderScreen(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes iconResId: Int,
    onAddItem: () -> Unit = {}
){
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small)
        ) {
            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.icon),
                imageVector = ImageVector.vectorResource(iconResId),
                contentDescription = stringResource(R.string.note_screen_title),
                tint = MaterialTheme.colorScheme.onSurface
            )

            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = MaterialTheme.typography.headlineLarge
            )
            IconButton(
                onClick = onAddItem
            ) {
                Icon(
                    modifier = Modifier.size(MaterialTheme.dimens.icon),
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = stringResource(R.string.header_screen_button,title),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small))
        HorizontalDivider(
            modifier = Modifier,
            thickness = MaterialTheme.dimens.smallThickness,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderScreenPreview(){
    InsightHeaderScreen(
        title = "Notes",
        iconResId = R.drawable.ic_note
    )
}
