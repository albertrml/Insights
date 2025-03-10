package br.com.arml.insights.model

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.source.InsightsRoomDatabase
import br.com.arml.insights.model.source.NoteDao
import br.com.arml.insights.model.source.TagDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class NoteDaoTest {

    private lateinit var db: InsightsRoomDatabase
    private lateinit var noteDao: NoteDao
    private lateinit var tagDao: TagDao
    private val tags = mockTags
    private val notes = mutableListOf<Note>()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context = context,
            klass = InsightsRoomDatabase::class.java
        ).build()
        tagDao = db.tagDao()
        noteDao = db.noteDao()
    }

    @Before
    fun clearTables() = runTest { db.clearAllTables() }

    @Before
    fun clearNotes() = runTest { notes.clear() }

    @After
    fun closeDb() = runTest { db.close() }

    private suspend fun populateWithNotes()  {
        tags.forEach { tag -> tagDao.insert(tag) }
        val allTags = tagDao.getAll().first()
        allTags.forEach { tag -> notes.addMockNotes(tag, 5) }
        notes.forEach { note -> noteDao.insert(note) }
    }

    /**  Insert and Get All Notes **/
    @Test
    fun whenInsertNotesAndGetAllIsSuccessful() = runTest {
        populateWithNotes()
        val allNotes = noteDao.getAll().first().map { it.copy(id = 0) }
        assertEquals(notes, allNotes)
    }

    @Test
    fun whenInsertNotesWithInvalidTagIdThrowsSQLiteConstraintException() = runTest {
        notes.addMockNotes(tags.first(), 1)
        assertThrows(SQLiteConstraintException::class.java){
            runBlocking { notes.forEach { note -> noteDao.insert(note) } }
        }
    }

    @Test
    fun whenInsertDuplicateNoteIdsThrowsSQLiteConstraintException() = runTest {
        tagDao.insert(tags.first())
        val tag = tagDao.getAll().first().first()
        notes.addMockNotes(tag, 1)
        noteDao.insert(notes.first())
        val insertedNote = noteDao.getAll().first().first()
        assertThrows(SQLiteConstraintException::class.java){
            runBlocking { noteDao.insert(insertedNote) }
        }
    }

    @Test
    fun whenTryToGetNotesButDbIsEmpty() = runTest {
        val allNotes = noteDao.getAll().first()
        assertEquals(emptyList<Note>(), allNotes)
    }

    /**  Get Note By Id **/
    @Test
    fun whenGetNoteByIdIsSuccessful() = runTest {
        populateWithNotes()
        val expectedNote = noteDao.getAll().first().first()
        val actualNote = noteDao.getById(expectedNote.id)
        assertEquals(expectedNote, actualNote)
    }

    @Test
    fun whenGetNoteByIdButNoteDoesNotExist() = runTest {
        populateWithNotes()
        val actualNote = noteDao.getById(100)
        assertNull(actualNote)
    }

    /** Get Notes By Tag **/
    @Test
    fun whenGetNotesByTagIsSuccessful() = runTest {
        populateWithNotes()
        val tag = tagDao.getAll().first().first()
        val expectedNotes = notes.filter { note -> note.tagId == tag.id }
        val actualNotes = noteDao.getByTag(tag.id).first().map { it.copy(id = 0) }
        assertEquals(expectedNotes, actualNotes)
    }

    @Test
    fun whenGetNotesByTagButTagDoesNotExist() = runTest {
        populateWithNotes()
        val actualNotes = noteDao.getByTag(100).first()
        assertEquals(emptyList<Note>(), actualNotes)
    }

    /** Delete Notes **/
    @Test
    fun whenDeleteNoteAndGetAllIsSuccessful() = runTest {
        tagDao.insert(tags.first())
        val tag = tagDao.getAll().first().first()
        notes.addMockNotes(tag, 1)
        noteDao.insert(notes.first())
        val noteToDelete = noteDao.getAll().first().first()
        noteDao.delete(noteToDelete)
        val allNotes = noteDao.getAll().first().map { it.copy(id = 0) }
        assertEquals(0,allNotes.size)
    }

    /** Update Notes **/
    @Test
    fun whenUpdateNoteChangesAreSuccessful() = runTest {
        tagDao.insert(tags.first())
        val tag = tagDao.getAll().first().first()
        notes.addMockNotes(tag, 1)
        noteDao.insert(notes.first())
        val noteBeforeUpdate = noteDao.getById(1)!!
        val noteToUpdate = noteBeforeUpdate.copy(
            title = "New Title",
            body = "New Body",
            situation = "New Situation"
        )
        noteDao.update(noteToUpdate)
        val actualNote = noteDao.getById(1)
        assertNotEquals(noteBeforeUpdate,actualNote)
        assertEquals(noteToUpdate, actualNote)
    }

    @Test
    fun whenUpdateNoteButNoteDoesNotExist() = runTest {
        tagDao.insert(tags.first())
        val tag = tagDao.getAll().first().first()
        notes.addMockNotes(tag, 1)
        noteDao.update(notes.first())
        val actualNote = noteDao.getById(1)
        assertNull(actualNote)
    }

}