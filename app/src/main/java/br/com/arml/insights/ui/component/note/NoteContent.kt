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
import androidx.compose.ui.unit.dp
import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.model.mock.createSampleNotes
import br.com.arml.insights.ui.theme.Gray300

@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    notes: List<NoteUi>,
    minSize: Dp = 180.dp,
    onClick: (NoteUi) -> Unit = {},
    onLongClick: (NoteUi) -> Unit = {}
){
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Adaptive(minSize = minSize),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(notes){ note ->
            NoteElement(
                modifier = Modifier.fillMaxWidth(),
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
            .border(1.dp, Gray300, RoundedCornerShape(0.dp))
            .combinedClickable(
                onClick = { onClick(note) },
                onLongClick = { onLongClick(note) }
            ),
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ){
        Column(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = note.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.padding(6.dp))
            Text(
                text = note.body,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 5
            )
            Spacer(modifier = Modifier.padding(6.dp))
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
            .padding(16.dp),
        notes = notes,
        minSize = 150.dp
    )
}