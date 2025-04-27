package br.com.arml.insights.model.entity

import br.com.arml.insights.utils.exception.NoteException
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import java.util.Date

class NoteUiTests {

    val cleanNoteUi = NoteUi(
        id = 0,
        title = "",
        body = "",
        situation = "",
        creationDate = Date(),
        tagId = 0
    )

    val validNoteUi = NoteUi(
        id = 1,
        title = "Valid Title",
        body = "valid body",
        situation = "valid situation",
        creationDate = Date(),
        tagId = 1
    )

    val validNote = Note(
        id = 1,
        title = "Valid Title",
        body = "valid body",
        situation = "valid situation",
        creationDate = validNoteUi.creationDate.time,
        tagId = 1
    )

    @Test
    fun `should return NoteUi from Note`() {
        assertEquals(
            validNoteUi,
            NoteUi.fromNote(validNote)
        )
    }

    @Test
    fun `should return Note from NoteUi`() {
        assertEquals(
            validNote,
            validNoteUi.toNote()
        )
    }

    @Test
    fun `should return cleanNoteUi when NoteUi is null`() {
        val newNoteUi = NoteUi.fromNote(null)
        assertEquals(
            cleanNoteUi.copy(creationDate = newNoteUi.creationDate),
            newNoteUi
        )
    }

    @Test
    fun `should throw NoteIsNullException when NoteUi is null`() {
        val invalidNoteUi = NoteUi.isValid(null)
        assertEquals(false, invalidNoteUi.first)
        assertTrue(
            invalidNoteUi.second is NoteException.NoteIsNullException
        )
    }

    @Test
    fun `should throw NoteTagIdException when tagId is lower 0`() {
        val invalidNoteUi = NoteUi.isValid(
            validNoteUi.copy(
                tagId = -1
            )
        )
        assertEquals(false, invalidNoteUi.first)
        assertTrue(
            invalidNoteUi.second is NoteException.NoteTagIdException
        )
        assertEquals(
            NoteException.NoteTagIdException().message,
            invalidNoteUi.second?.message
        )
    }

    @Test
    fun `should throw NoteTitleSizeException when title length is not in 3 to 20`() {
        val invalidNoteUi = NoteUi.isValid(
            validNoteUi.copy(
                title = "a".repeat(21)
            )
        )
        assertEquals(false, invalidNoteUi.first)
        assertTrue(
            invalidNoteUi.second is NoteException.NoteTitleSizeException
        )
        assertEquals(
            NoteException.NoteTitleSizeException().message,
            invalidNoteUi.second?.message
        )
    }

    @Test
    fun `should throw NoteBodySizeException when body length is not in 0 to 1000`() {
        val invalidNoteUi = NoteUi.isValid(
            validNoteUi.copy(
                body = "a".repeat(1001)
            )
        )
        assertEquals(false, invalidNoteUi.first)
        assertTrue(
            invalidNoteUi.second is NoteException.NoteBodySizeException
        )
        assertEquals(
            NoteException.NoteBodySizeException().message,
            invalidNoteUi.second?.message
        )
    }

    @Test
    fun `should throw NoteSituationSizeException when situation length is not in 0 to 20`() {
        val invalidNoteUi = NoteUi.isValid(
            validNoteUi.copy(
                situation = "a".repeat(21)
            )
        )
        assertEquals(false, invalidNoteUi.first)
        assertTrue(
            invalidNoteUi.second is NoteException.NoteSituationSizeException
        )
        assertEquals(
            NoteException.NoteSituationSizeException().message,
            invalidNoteUi.second?.message
        )
    }

    @Test
    fun `should return true and null when NoteUi is valid`() {
        val result = NoteUi.isValid(validNoteUi)
        assertEquals(Pair(true,null), result)
    }

}