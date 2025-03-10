package br.com.arml.insights.model.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.mock.addMockNotes
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.model.source.InsightsRoomDatabase
import br.com.arml.insights.model.source.NoteDao
import br.com.arml.insights.model.source.TagDao
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.tools.until
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class NoteRepositoryTest {

    private lateinit var noteRepository: NoteRepository
    private lateinit var tagRepository: TagRepository
    private lateinit var noteDao: NoteDao
    private lateinit var tagDao: TagDao
    private lateinit var db: InsightsRoomDatabase
    private val tags = mockTags
    private val notes = mutableListOf<Note>()

    @Before
    fun setupDb(){
        val ctx = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context = ctx,
            klass = InsightsRoomDatabase::class.java
        ).build()

        noteDao = db.noteDao()
        tagDao = db.tagDao()
        noteRepository = NoteRepository(noteDao)
        tagRepository = TagRepository(tagDao)
    }

    @Before
    fun clearDb() = db.clearAllTables()

    @After
    fun closeDb() = db.close()

    private suspend fun populateWithTags() {
        tags.forEach { tag -> tagDao.insert(tag) }
        notes.forEach { note -> noteDao.insert(note) }
    }

    /** Insert Notes and Get All Notes **/
    @Test
    fun whenInsertNotesIsSuccessful() = runTest {
        tagDao.insert(tags.first)
        val tag = tagDao.getById(1)!!
        notes.addMockNotes(tag, 1)
        val expectedNote = notes.first()

        combine(
            noteRepository.insert(expectedNote),
            noteRepository.getAll()
        ){ insertResponse, getAllResponse ->
            if(insertResponse is Response.Success && getAllResponse is Response.Success){
                val actualNotes = getAllResponse.result.first().copy(id = 0)
                assert(expectedNote == actualNotes)
            }
            getAllResponse
        }.until { it is Response.Success }.collect()
    }

    @Test
    fun whenInsertNotesWithInvalidTagIdThrowsSQLiteConstraintException() = runTest {  }

    @Test
    fun whenInsertDuplicateNoteIdsThrowsSQLiteConstraintException() = runTest {  }

    @Test
    fun whenTryToGetNotesButDbIsEmpty() = runTest {  }

    /** Get All Note ***/
    @Test
    fun whenGetAllNotesIsSuccessful() = runTest {  }

    @Test
    fun whenGetAllNotesButDbIsEmpty() = runTest {  }

    /** Get Note By Id **/
    @Test
    fun whenGetNoteByIdIsSuccessful() = runTest {  }

    @Test
    fun whenGetNoteByIdButNoteDoesNotExist() = runTest {  }

    /** Get Notes By Tag **/
    @Test
    fun whenGetNotesByTagIsSuccessful() = runTest {  }

    @Test
    fun whenGetNotesByTagButTagDoesNotExist() = runTest {  }

    /** Delete Notes **/
    @Test
    fun whenDeleteNoteAndGetAllIsSuccessful() = runTest {  }

    /** Update Notes **/
    @Test
    fun whenUpdateNoteChangesAreSuccessful() = runTest {  }

    @Test
    fun whenUpdateNoteButNoteDoesNotExist() = runTest {  }

}