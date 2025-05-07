package br.com.arml.insights.ui.component.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.arml.insights.R
import br.com.arml.insights.ui.theme.dimens

@Composable
fun InsightHeaderForms(
    modifier: Modifier = Modifier,
    title: String,
    onClickClose: () -> Unit = {}
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        IconButton(
            onClick = onClickClose
        ) {
            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.icon),
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.note_screen_close_menu,title)
            )
        }
    }
}