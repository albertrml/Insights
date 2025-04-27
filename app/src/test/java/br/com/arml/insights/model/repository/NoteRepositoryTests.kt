package br.com.arml.insights.model.repository

import android.database.sqlite.SQLiteConstraintException
import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.mock.createSampleNotes
import br.com.arml.insights.model.source.NoteDao
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.exception.InsightException
import br.com.arml.insights.utils.tools.until
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NoteRepositoryTests {

    val noteDao = mockk<NoteDao>()
    val noteRepository = NoteRepository(noteDao)

    @Test
    fun `should emit Success when Insert Note Is Successful`() = runTest {
        val fakeNote = Note(
            id = 0, // auto-generated
            title = "Test Note",
            body = "This is a body.",
            situation = "Active",
            creationDate = System.currentTimeMillis(),
            tagId = 1 // it must be associated with an existent Tag
        )

        coEvery { noteDao.insert(fakeNote) } just Runs

        noteRepository
            .insert(fakeNote)
            .until { response -> response is Response.Success }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success -> assertTrue(true)
                    is Response.Failure -> assertTrue(false)
                }
            }
    }

    @Test
    fun `should emit Failure when Insert Note With Invalid TagId`() = runTest {
        val fakeNote = Note(
            id = 0,
            title = "Test Note",
            body = "This is a body.",
            situation = "Active",
            creationDate = System.currentTimeMillis(),
            tagId = 9999 // ID mock an invalid FK
        )

        // Mock: It mock a FK failure
        coEvery { noteDao.insert(fakeNote) } throws SQLiteConstraintException()

        noteRepository
            .insert(fakeNote)
            .until { response -> response is Response.Failure }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success -> assertTrue(false)
                    is Response.Failure -> {
                        assertTrue(response.exception is SQLiteConstraintException)
                    }
                }
            }
    }

    @Test
    fun `should emit Failure when insert note with duplicated id`() = runTest {
        val fakeNote = Note(
            id = 1,
            title = "Test Note",
            body = "This is a body.",
            situation = "Active",
            creationDate = System.currentTimeMillis(),
            tagId = 1
        )
        val database = mutableSetOf<Int>(1)
        val tagSlot = slot<Note>()

        coEvery { noteDao.insert(capture(tagSlot)) } answers {
            val newNote = tagSlot.captured
            if (database.contains(newNote.id)){
                throw SQLiteConstraintException()
            }else {
                database.add(database.size + 1)
            }
        }

        noteRepository.insert(fakeNote)
            .until { response -> response is Response.Failure }
            .collect { response ->
                when(response){
                    is Response.Loading -> assertTrue(true)
                    is Response.Success -> assertTrue(false)
                    is Response.Failure -> {
                        assertTrue(response.exception is SQLiteConstraintException)
                    }
                }
            }
    }

    @Test
    fun `should emit Success when Delete Note Is Successful`() = runTest {
        val fakeNote = Note(
            id = 1,
            title = "Test Note",
            body = "This is a body.",
            situation = "Active",
            creationDate = System.currentTimeMillis(),
            tagId = 1
        )
        val database = mutableSetOf(fakeNote)
        val noteSlot = slot<Note>()

        coEvery { noteDao.getById(fakeNote.id) } returns fakeNote

        coEvery { noteDao.delete(capture(noteSlot)) } answers {
            val deletedNote = noteSlot.captured
            if (database.contains(deletedNote)){
                database.remove(deletedNote)
            }
            Unit
        }

        noteRepository
            .delete(fakeNote)
            .until { response -> response is Response.Success }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success -> {
                        assertTrue(database.isEmpty())
                    }
                    is Response.Failure -> assertTrue(false)
                }
            }

    }

    @Test
    fun `should emit Failure when Note does not exist`() = runTest {
        val fakeNote = Note(
            id = 1,
            title = "Test Note",
            body = "This is a body.",
            situation = "Active",
            creationDate = System.currentTimeMillis(),
            tagId = 1
        )

        coEvery { noteDao.getById(fakeNote.id) } returns null

        noteRepository
            .delete(fakeNote)
            .until { response -> response is Response.Failure }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success -> assertTrue(false)
                    is Response.Failure -> assertTrue(
                        response.exception is InsightException.NoteNotFoundException
                    )
                }
            }
    }

    @Test
    fun `should emit Failure when Note is not the same`() = runTest{
        val fakeNote = Note(
            id = 1,
            title = "Test Note",
            body = "This is a body.",
            situation = "Active",
            creationDate = System.currentTimeMillis(),
            tagId = 1
        )
        val noteForDelete = Note(
            id = 1,
            title = "Test Note 2",
            body = "This is a body 2.",
            situation = "Active 2",
            creationDate = System.currentTimeMillis(),
            tagId = 1
        )

        coEvery{ noteDao.getById(noteForDelete.id) } returns fakeNote

        noteRepository.delete(noteForDelete)
            .until { response -> response is Response.Failure }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success -> assertTrue(false)
                    is Response.Failure -> assertTrue(
                        response.exception is InsightException.NoteNotFoundException
                    )
                }
            }
    }

    @Test
    fun `should emit Success when Update Note Is Successful`() = runTest{
        val fakeNote = Note(
            id = 1,
            title = "Test Note",
            body = "This is a body.",
            situation = "Active",
            creationDate = System.currentTimeMillis(),
            tagId = 1
        )
        val updatedNote = Note(
            id = 1,
            title = "Test Note 2",
            body = "This is a body 2.",
            situation = "Active 2",
            creationDate = System.currentTimeMillis(),
            tagId = 1
        )
        val database = mutableSetOf(fakeNote)

        coEvery { noteDao.update(updatedNote) } answers {
            database.remove(fakeNote)
            database.add(updatedNote)
            Unit
        }

        noteRepository.update(updatedNote)
            .until { response -> response is Response.Success }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success -> {
                        assertTrue(database.contains(updatedNote))
                        assertTrue(!database.contains(fakeNote))
                    }
                    is Response.Failure -> assertTrue(false)
                }
            }
    }

    @Test
    fun `should emit Success when Get All Notes Is Successful`() = runTest{
        val fakeNotes = createSampleNotes().mapIndexed { index, noteUi ->
            noteUi.toNote().copy(id = index + 1)
        }
        coEvery { noteDao.getAll() } returns flow<List<Note>> { fakeNotes }

        noteRepository.getAll()
            .until { response -> response is Response.Success }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success -> {
                        assertEquals(fakeNotes, response.result)
                    }
                    is Response.Failure -> assertTrue(false)
                }
            }

    }

    @Test
    fun `should emit Success when Get Notes By Tag Is Successful`() = runTest{
        val fakeNotes = createSampleNotes().mapIndexed { index, noteUi ->
            noteUi.toNote().copy(id = index + 1)
        }
        val tagId = 1
        coEvery { noteDao.getByTag(tagId) } returns flow { fakeNotes.find { tagId == it.tagId } }

        noteRepository.getByTag(tagId)
            .until { response -> response is Response.Success }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success -> {
                        assertEquals(fakeNotes.find { tagId == it.tagId }, response.result)
                    }
                    is Response.Failure -> assertTrue(false)
                }
            }

    }
}