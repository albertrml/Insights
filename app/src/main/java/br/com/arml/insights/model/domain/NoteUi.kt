package br.com.arml.insights.model.domain

import br.com.arml.insights.model.entity.NoteEntity
import br.com.arml.insights.utils.exception.NoteException
import java.text.DateFormat
import java.util.Date

const val MIN_TITLE_LENGTH = 3
const val MAX_TITLE_LENGTH = 30
const val MIN_BODY_LENGTH = 0
const val MAX_BODY_LENGTH = 1000
const val MIN_SITUATION_LENGTH = 0
const val MAX_SITUATION_LENGTH = 30

data class NoteUi (
    val id: Long = 0,
    val title: String,
    val body: String,
    val situation: String,
    val creationDate: Date
){
    fun toNoteEntity() = NoteEntity(
        id = id,
        title = title,
        body = body,
        situation = situation,
        creationDate = creationDate.time,
    )

    fun getCreationDate(): String{
        val formatter = DateFormat.getDateTimeInstance()
        return formatter.format(creationDate)
    }

    companion object{
        fun fromNoteEntity(noteEntity: NoteEntity?) = noteEntity?.let {
            NoteUi(
                id = it.id,
                title = it.title,
                body = it.body,
                situation = it.situation,
                creationDate = Date(it.creationDate),
            )
        } ?: NoteUi(
            id = 0,
            title = "",
            body = "",
            situation = "",
            creationDate = Date(),
        )

        fun isValid(noteUi: NoteUi?) = when {
            noteUi == null ->
                throw NoteException.NoteIsNullException()

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