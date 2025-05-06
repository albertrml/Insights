package br.com.arml.insights.model.entity

import br.com.arml.insights.utils.exception.NoteException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class NoteUi (
    val id: Int = 0,
    val title: String,
    val body: String,
    val situation: String,
    val creationDate: Date,
    val tagId: Int
){
    fun toNote() = Note(
        id = id,
        title = title,
        body = body,
        situation = situation,
        creationDate = creationDate.time,
        tagId = tagId
    )

    fun getCreationDate(): String{
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm XXX", Locale.getDefault())
        formatter.timeZone = TimeZone.getDefault()
        return formatter.format(creationDate)
    }

    companion object{
        fun fromNote(note: Note?) = note?.let {
            NoteUi(
                id = note.id,
                title = note.title,
                body = note.body,
                situation = note.situation,
                creationDate = Date(note.creationDate),
                tagId = note.tagId
            )
        } ?: NoteUi(
            id = 0,
            title = "",
            body = "",
            situation = "",
            creationDate = Date(),
            tagId = 0
        )

        fun isValid(noteUi: NoteUi?): Pair<Boolean, NoteException?> = when {
            noteUi == null ->
                false to NoteException.NoteIsNullException()

            noteUi.tagId < 0 ->
                false to NoteException.NoteTagIdException()

            noteUi.title.length !in 3..30 ->
                false to NoteException.NoteTitleSizeException()

            noteUi.body.length !in 0..1000 ->
                false to NoteException.NoteBodySizeException()

            noteUi.situation.length !in 0..30 ->
                false to NoteException.NoteSituationSizeException()

            else -> true to null
        }

    }
}