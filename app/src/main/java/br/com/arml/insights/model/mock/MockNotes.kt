package br.com.arml.insights.model.mock

import br.com.arml.insights.model.domain.NoteUi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val mockTitle = "Note title"
const val mockBody = "Note body"
const val mockSituation = "Note situation"
val mockCreationDateInMilli = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
    .parse("10/07/2026")
    ?.time ?: 0L
const val mockTimestep = 86400000L // 1 dia em milissegundos

fun createSampleNotes(quantity: Int) = List(quantity) { index ->
    NoteUi(
        title = "$mockTitle $index",
        situation = "$mockSituation $index",
        body = "$mockBody $index",
        creationDate = Date(mockCreationDateInMilli + (index * mockTimestep))
    )
}

val mockNoteUIs = createSampleNotes(5)
val mockNoteEntities = mockNoteUIs.map { noteUi -> noteUi.toNoteEntity() }