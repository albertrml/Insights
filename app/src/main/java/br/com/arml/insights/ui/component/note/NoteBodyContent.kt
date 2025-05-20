package br.com.arml.insights.ui.component.note

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.data.ShowResults

@Composable
fun NoteBodyContent(
    modifier: Modifier = Modifier,
    response: Response<List<NoteUi>>,
    onEditNote: (NoteUi) -> Unit = {},
    onDeleteNote: (NoteUi) -> Unit = {}
){
    response.ShowResults(
        successContent = { notes ->
            NoteList(
                modifier = modifier,
                notes = notes,
                onClick = onEditNote,
                onLongClick = onDeleteNote
            )
        }
    )
}

