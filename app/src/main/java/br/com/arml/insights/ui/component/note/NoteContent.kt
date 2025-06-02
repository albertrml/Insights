package br.com.arml.insights.ui.component.note

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.model.mock.createSampleNotes
import br.com.arml.insights.ui.theme.dimens

@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    notes: List<NoteUi>,
    minSize: Dp = MaterialTheme.dimens.xLargeIcon,
    onClick: (NoteUi) -> Unit = {},
    onLongClick: (NoteUi) -> Unit = {}
){
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Adaptive(minSize = minSize),
        verticalItemSpacing = MaterialTheme.dimens.mediumSpacing,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.mediumSpacing)
    ){
        items(notes){ note ->
            NoteElement(
                modifier = Modifier,
                note = note,
                onClick = onClick,
                onLongClick = onLongClick
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteElement(
    modifier: Modifier = Modifier,
    note: NoteUi,
    onClick: (NoteUi) -> Unit = {},
    onLongClick: (NoteUi) -> Unit = {}
){
    Card(
        modifier = modifier
            .border(
                width = MaterialTheme.dimens.smallThickness,
                color = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(MaterialTheme.dimens.smallCornerRadius)
            )
            .combinedClickable(
                onClick = { onClick(note) },
                onLongClick = { onLongClick(note) }
            ),
        elevation = CardDefaults.cardElevation(MaterialTheme.dimens.mediumElevation),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.smallPadding),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.smallSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.padding(MaterialTheme.dimens.smallSpacing))
            Text(
                text = note.body,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 5
            )
            Spacer(modifier = Modifier.padding(MaterialTheme.dimens.smallSpacing))
            Text(
                text = note.getCreationDate(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteListPreview(){
    val notes = createSampleNotes()

    NoteList(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.smallPadding),
        notes = notes
    )
}