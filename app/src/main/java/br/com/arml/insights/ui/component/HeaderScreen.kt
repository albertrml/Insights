package br.com.arml.insights.ui.component

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.arml.insights.ui.theme.Gray500

@Composable
fun HeaderScreen(
    modifier: Modifier = Modifier,
    title: String,
    onAddItem: () -> Unit = {}
){
    Column(
        modifier = modifier,
    ) {
        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = MaterialTheme.typography.headlineLarge
            )
            IconButton(
                onClick = onAddItem
            ) {
                Icon(
                    modifier = Modifier.size(42.dp),
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add New $title",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        HorizontalDivider(Modifier, 2.dp, Gray500)
    }
}