package br.com.arml.insights.model.viewmodel

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
import br.com.arml.insights.viewmodel.updateinsight.UpdateInsightUiEvent
import br.com.arml.insights.viewmodel.updateinsight.UpdateInsightViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UpdateInsightViewModelTest {

    private lateinit var viewModel: UpdateInsightViewModel
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
        viewModel = UpdateInsightViewModel(tagRepository,noteRepository)
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

    /** Fetch Insight **/
    @Test
    fun whenFetchInsightIsSuccessful() = runTest {
        populateDb()
        val expectedNote = noteDao.getById(1)!!

        viewModel.onEvent(UpdateInsightUiEvent.OnFetchInsight(expectedNote.id))
        viewModel.uiState
            .map { it.fetchInsightState }
            .until { it is Response.Success }
            .collectLatest { response ->
                when(response){
                    is Response.Success -> {
                        val actualNote = response.result
                        assertEquals(expectedNote,actualNote)
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

    /** Fetch Tags **/
    @Test
    fun whenFetchTagsIsSuccessful() = runTest {
        populateDb()
        val sortedTags = tags.sortedBy { it.name }
        viewModel.onEvent(UpdateInsightUiEvent.OnFetchTags)
        viewModel.uiState
            .map { it.fetchTagsState }
            .until { it is Response.Success }
            .collectLatest{ response ->
                when(response){
                    is Response.Success -> {
                        val actualTags = response.result.map { it.copy(id = 0) }
                        actualTags.forEachIndexed { index, tag ->
                            if(actualTags.isNotEmpty() && index < actualTags.size - 1) {
                                assertTrue(tag.name <= actualTags[index + 1].name)
                            }
                        }
                        assertEquals(sortedTags,actualTags)
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

    /** Update Insight **/
    @Test
    fun whenUpdateInsightIsSuccessful() = runTest {
        populateDb()
        val tagsById = noteDao.getByTag(1).first()
        val note = tagsById.first()

        viewModel.onEvent(
            UpdateInsightUiEvent.OnUpdateInsight(
                id = note.id,
                title = note.title,
                situation = note.situation,
                body = note.body,
                creationDate = note.creationDate,
                tagId = 2
            )
        )

        viewModel.uiState
            .map { it.updateInsightState }
            .until { it is Response.Success }
            .collectLatest { response ->
                when(response){
                    is Response.Success -> {
                        val tagsById1 = noteDao.getByTag(1).first()
                        val tagsById2 = noteDao.getByTag(2).first()
                        val actualNote = noteDao.getById(note.id)!!
                        assertTrue(tagsById1.size < tagsById2.size)
                        assertFalse(tagsById1.contains(actualNote))
                        assertTrue(tagsById2.contains(actualNote))
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

}