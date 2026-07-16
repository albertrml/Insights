package br.com.arml.insights.model.repository

import android.database.sqlite.SQLiteConstraintException
import br.com.arml.core.flow.until
import br.com.arml.insights.model.entity.NoteEntity
import br.com.arml.insights.model.mock.createSampleNotes
import br.com.arml.insights.model.source.NoteDao
import br.com.arml.core.response.Response
import br.com.arml.insights.utils.exception.InsightException
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

/**
 * Situation: Now, the new database model uses NoteTagLinkEntity to relate notes with tags,
 * allowing relation n:n between them. The tagId FK in the NoteEntity is not used anymore,
 * so tagId is removed from the NoteEntity and NoteUi.
 * TODO: rewrite them using TagWithNotes or NoteWithTags
 * **/
class NoteEntityRepositoryTests {

    val noteDao = mockk<NoteDao>()
    val noteRepository = NoteRepository(noteDao)

    @Test
    fun `should emit Success when Insert Note Is Successful`() = runTest {
        val fakeNoteEntity = NoteEntity(
            id = 0, // auto-generated
            title = "Test Note",
            body = "This is a body.",
            situation = "Active",
            creationDate = System.currentTimeMillis(),
            //tagId = 1 // it must be associated with an existent Tag
        )

        coEvery { noteDao.insert(fakeNoteEntity) } just Runs

        noteRepository
            .insert(fakeNoteEntity)
            .until { response -> response is Response.Success<*> }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success<*> -> assertTrue(true)
                    is Response.Failure -> assertTrue(false)
                }
            }
    }

    @Test
    fun `should emit Failure when Insert Note With Invalid TagId`() = runTest {
        val fakeNoteEntity = NoteEntity(
            id = 0,
            title = "Test Note",
            body = "This is a body.",
            situation = "Active",
            creationDate = System.currentTimeMillis(),
            //tagId = 9999 // ID mock an invalid FK
        )

        coEvery { noteDao.insert(fakeNoteEntity) } throws SQLiteConstraintException()

        noteRepository
            .insert(fakeNoteEntity)
            .until { response -> response is Response.Failure }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success<*> -> assertTrue(false)
                    is Response.Failure -> {
                        assertTrue(response.exception is SQLiteConstraintException)
                    }
                }
            }
    }

    @Test
    fun `should emit Failure when insert note with duplicated id`() = runTest {
        /*val fakeNoteEntity = NoteEntity(
            id = 1,
            title = "Test Note",
            body = "This is a body.",
            situation = "Active",
            creationDate = System.currentTimeMillis(),
            //tagId = 1
        )
        val database = mutableSetOf(1)
        val tagSlot = slot<NoteEntity>()

        coEvery { noteDao.insert(capture(tagSlot)) } answers {
            val newNote = tagSlot.captured
            if (database.contains(newNote.id)){
                throw SQLiteConstraintException()
            }else {
                database.add(database.size + 1)
            }
        }

        noteRepository.insert(fakeNoteEntity)
            .until { response -> response is Response.Failure }
            .collect { response ->
                when(response){
                    is Response.Loading -> assertTrue(true)
                    is Response.Success<*> -> assertTrue(false)
                    is Response.Failure -> {
                        assertTrue(response.exception is SQLiteConstraintException)
                    }
                }
            }*/
    }

    @Test
    fun `should emit Success when Delete Note Is Successful`() = runTest {
        val fakeNoteEntity = NoteEntity(
            id = 1,
            title = "Test Note",
            body = "This is a body.",
            situation = "Active",
            creationDate = System.currentTimeMillis(),
            //tagId = 1
        )
        val database = mutableSetOf(fakeNoteEntity)
        val noteEntitySlot = slot<NoteEntity>()

        coEvery { noteDao.getById(fakeNoteEntity.id) } returns fakeNoteEntity

        coEvery { noteDao.delete(capture(noteEntitySlot)) } answers {
            val deletedNote = noteEntitySlot.captured
            if (database.contains(deletedNote)){
                database.remove(deletedNote)
            }
        }

        noteRepository
            .delete(fakeNoteEntity)
            .until { response -> response is Response.Success<*> }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success<*> -> {
                        assertTrue(database.isEmpty())
                    }
                    is Response.Failure -> assertTrue(false)
                }
            }

    }

    @Test
    fun `should emit Failure when Note does not exist`() = runTest {
        val fakeNoteEntity = NoteEntity(
            id = 1,
            title = "Test Note",
            body = "This is a body.",
            situation = "Active",
            creationDate = System.currentTimeMillis(),
            //tagId = 1
        )

        coEvery { noteDao.getById(fakeNoteEntity.id) } returns null

        noteRepository
            .delete(fakeNoteEntity)
            .until { response -> response is Response.Failure }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success<*> -> assertTrue(false)
                    is Response.Failure -> assertTrue(
                        response.exception is InsightException.NoteNotFoundException
                    )
                }
            }
    }

    @Test
    fun `should emit Failure when Note is not the same`() = runTest{
        val fakeNoteEntity = NoteEntity(
            id = 1,
            title = "Test Note",
            body = "This is a body.",
            situation = "Active",
            creationDate = System.currentTimeMillis(),
            //tagId = 1
        )
        val noteEntityForDelete = NoteEntity(
            id = 1,
            title = "Test Note 2",
            body = "This is a body 2.",
            situation = "Active 2",
            creationDate = System.currentTimeMillis(),
            //tagId = 1
        )

        coEvery{ noteDao.getById(noteEntityForDelete.id) } returns fakeNoteEntity

        noteRepository.delete(noteEntityForDelete)
            .until { response -> response is Response.Failure }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success<*> -> assertTrue(false)
                    is Response.Failure -> assertTrue(
                        response.exception is InsightException.NoteNotFoundException
                    )
                }
            }
    }

    @Test
    fun `should emit Success when Update Note Is Successful`() = runTest{
        val fakeNoteEntity = NoteEntity(
            id = 1,
            title = "Test Note",
            body = "This is a body.",
            situation = "Active",
            creationDate = System.currentTimeMillis(),
            //tagId = 1
        )
        val updatedNoteEntity = NoteEntity(
            id = 1,
            title = "Test Note 2",
            body = "This is a body 2.",
            situation = "Active 2",
            creationDate = System.currentTimeMillis(),
            //tagId = 1
        )
        val database = mutableSetOf(fakeNoteEntity)

        coEvery { noteDao.update(updatedNoteEntity) } answers {
            database.remove(fakeNoteEntity)
            database.add(updatedNoteEntity)
        }

        noteRepository.update(updatedNoteEntity)
            .until { response -> response is Response.Success<*> }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success<*> -> {
                        assertTrue(database.contains(updatedNoteEntity))
                        assertTrue(!database.contains(fakeNoteEntity))
                    }
                    is Response.Failure -> assertTrue(false)
                }
            }
    }

    @Test
    fun `should emit Success when Get All Notes Is Successful`() = runTest{
        val fakeNotes = createSampleNotes(10).mapIndexed { index, noteUi ->
            noteUi.toNoteEntity().copy(id = index + 1L)
        }
        coEvery { noteDao.getAll() } returns flow { }

        noteRepository.getAll()
            .until { response -> response is Response.Success<*> }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success<*> -> {
                        assertEquals(fakeNotes, response.result)
                    }
                    is Response.Failure -> assertTrue(false)
                }
            }

    }

    @Test
    fun `should emit Success when Get Notes By Tag Is Successful`() = runTest{
        /*val fakeNotes = createSampleNotes(10).mapIndexed { index, noteUi ->
            noteUi.toNoteEntity().copy(id = index + 1)
        }
        val tagId = 1
        coEvery { noteDao.getByTag(tagId) } returns flow { fakeNotes.find { tagId == it.tagId } }

        noteRepository.getByTag(tagId)
            .until { response -> response is Response.Success<*> }
            .collect { response ->
                when (response) {
                    is Response.Loading -> assertTrue(true)
                    is Response.Success<*> -> {
                        assertEquals(fakeNotes.find { tagId == it.tagId }, response.result)
                    }
                    is Response.Failure -> assertTrue(false)
                }
            }*/
    }
}