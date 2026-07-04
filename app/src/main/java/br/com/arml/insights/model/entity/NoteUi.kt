package br.com.arml.insights.model.entity

import br.com.arml.insights.utils.exception.NoteException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val MIN_TITLE_LENGTH = 3
const val MAX_TITLE_LENGTH = 30
const val MIN_BODY_LENGTH = 0
const val MAX_BODY_LENGTH = 1000
const val MIN_SITUATION_LENGTH = 0
const val MAX_SITUATION_LENGTH = 30

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

        /*fun isValid(noteUi: NoteUi?): Pair<Boolean, NoteException?> = when {
            noteUi == null ->
                false to NoteException.NoteIsNullException()

            noteUi.tagId < 0 ->
                false to NoteException.NoteTagIdException()

            noteUi.title.length !in MIN_TITLE_LENGTH..MAX_TITLE_LENGTH ->
                false to NoteException.NoteTitleSizeException()

            noteUi.body.length !in MIN_BODY_LENGTH..MAX_BODY_LENGTH ->
                false to NoteException.NoteBodySizeException()

            noteUi.situation.length !in MIN_SITUATION_LENGTH..MAX_SITUATION_LENGTH ->
                false to NoteException.NoteSituationSizeException()

            else -> true to null
        }*/

        fun isValid(noteUi: NoteUi?) = when {
            noteUi == null ->
                throw NoteException.NoteIsNullException()

            noteUi.tagId < 0 ->
                throw NoteException.NoteTagIdException()

            noteUi.title.length !in MIN_TITLE_LENGTH..MAX_TITLE_LENGTH ->
                throw NoteException.NoteTitleSizeException()

            noteUi.body.length !in MIN_BODY_LENGTH..MAX_BODY_LENGTH ->
                throw NoteException.NoteBodySizeException()

            noteUi.situation.length !in MIN_SITUATION_LENGTH..MAX_SITUATION_LENGTH ->
                throw NoteException.NoteSituationSizeException()

            else -> Unit
        }

    }
}