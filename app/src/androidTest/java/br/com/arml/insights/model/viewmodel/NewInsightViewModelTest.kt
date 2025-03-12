package br.com.arml.insights.model.viewmodel

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.mock.addMockNotes
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.model.repository.NoteRepository
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.model.source.InsightsRoomDatabase
import br.com.arml.insights.model.source.NoteDao
import br.com.arml.insights.model.source.TagDao
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.tools.until
import br.com.arml.insights.viewmodel.newinsight.NewInsightUiEvent
import br.com.arml.insights.viewmodel.newinsight.NewInsightViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NewInsightViewModelTest {

    private lateinit var viewModel: NewInsightViewModel
    private lateinit var tagRepository: TagRepository
    private lateinit var noteRepository: NoteRepository
    private lateinit var tagDao: TagDao
    private lateinit var noteDao: NoteDao
    private lateinit var db: InsightsRoomDatabase
    private val tags = mockTags
    private val notes = mutableListOf<Note>()

    @Before
    fun setupDb() {
        db = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            klass = InsightsRoomDatabase::class.java
        ).build()

        tagDao = db.tagDao()
        noteDao = db.noteDao()
        tagRepository = TagRepository(tagDao)
        noteRepository = NoteRepository(noteDao)
        viewModel = NewInsightViewModel(tagRepository,noteRepository)
    }

    @Before
    fun clearTables() = db.clearAllTables()

    @After
    fun closeDb() = db.close()

    private suspend fun populateDb() {
        tags.forEach { tag -> tagDao.insert(tag) }

        val allTags = tagDao.getAll().first()
        allTags.forEach { tag -> notes.addMockNotes(tag, 5) }

        notes.forEach { note -> noteDao.insert(note) }
    }

    /** Fetch Tags **/
    @Test
    fun whenFetchTagsIsSuccessful() = runTest {
        populateDb()
        viewModel.onEvent(NewInsightUiEvent.OnFetchTags)
        viewModel.uiState
            .map { it.fetchTags }
            .until { it is Response.Success }
            .collectLatest { response ->
                when(response){
                    is Response.Success -> {
                        val actualTags = response.result
                        actualTags.forEachIndexed { index, tag ->
                            if(actualTags.isNotEmpty() && index < actualTags.size - 1) {
                                assertTrue(tag.name <= actualTags[index + 1].name)
                            }
                        }
                    }
                    is Response.Loading -> {}
                    is Response.Failure -> {
                        assertTrue(
                            "The answer should be a success response",
                            false
                        )
                    }
                }
            }
    }

    /** Insert Note **/
    @Test
    fun whenInsertNoteIsSuccessful() = runTest {
        tags.forEach { tag -> tagDao.insert(tag) }
        val tag = tagDao.getById(1)!!
        viewModel.onEvent(
            NewInsightUiEvent.OnInsertInsight(
                title = "Test",
                situation = "Test",
                body = "Test",
                tagId = tag.id
            )
        )
        viewModel.uiState
            .map { it.insertInsightState }
            .until { it is Response.Success }
            .collect{ response ->
                when(response){
                    is Response.Success -> {
                        val actualNote = noteDao.getById(1)
                        assertNotNull(actualNote)
                        assertTrue(actualNote!!.title == "Test")
                        assertTrue(actualNote.situation == "Test")
                        assertTrue(actualNote.body == "Test")
                        assertTrue(actualNote.tagId == tag.id)
                    }
                    is Response.Loading -> {}
                    is Response.Failure -> {
                        assertTrue(
                            "The answer should be a success response",
                            false
                        )
                    }
                }
            }
    }

    @Test
    fun whenInsertNoteIsUnsuccessful() = runTest {
        tags.forEach { tag -> tagDao.insert(tag) }
        viewModel.onEvent(
            NewInsightUiEvent.OnInsertInsight(
                title = "Test",
                situation = "Test",
                body = "Test",
                tagId = 0
            )
        )
        viewModel.uiState
            .map { it.insertInsightState }
            .until { it is Response.Failure }
            .collect{ response ->
                when(response){
                    is Response.Success -> {
                        assertTrue(
                            "The answer should be a success response",
                            false
                        )
                    }
                    is Response.Loading -> {}
                    is Response.Failure -> {
                        assertTrue(
                            response.exception.message,
                            response.exception is SQLiteConstraintException
                        )
                    }
                }
            }
    }

}