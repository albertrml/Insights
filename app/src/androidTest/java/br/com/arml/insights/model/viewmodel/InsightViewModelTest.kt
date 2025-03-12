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
import br.com.arml.insights.utils.exception.InsightException
import br.com.arml.insights.utils.tools.until
import br.com.arml.insights.viewmodel.insight.InsightUiEvent
import br.com.arml.insights.viewmodel.insight.InsightViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class InsightViewModelTest {

    private lateinit var viewModel: InsightViewModel
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
        viewModel = InsightViewModel(noteRepository)
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

    /** Delete Note **/
    @Test
    fun whenDeleteNoteIsSuccessful() = runTest {
        populateDb()
        val noteFromDb = noteDao.getById(1)!!
        viewModel.onEvent(InsightUiEvent.OnDeleteNote(noteFromDb))
        viewModel.uiState
            .map { it.deleteNoteState }
            .until { it is Response.Success }
            .collectLatest { response ->
                when(response){
                    is Response.Success -> {
                        assertNull(noteDao.getById(noteFromDb.id))
                    }
                    is Response.Loading -> {}
                    is Response.Failure -> {
                        assertFalse(
                            response.exception.message,
                            true
                        )
                    }
                }
            }
    }

    @Test
    fun whenDeleteWrongNoteWithValidIdThrowsNoteNotFoundException() = runTest {
        populateDb()
        val noteFromDb = noteDao.getById(1)!!.copy(id = 2)
        viewModel.onEvent(InsightUiEvent.OnDeleteNote(noteFromDb))
        viewModel.uiState
            .map { it.deleteNoteState }
            .until { it is Response.Failure }
            .collectLatest { response ->
                when (response) {
                    is Response.Success -> {
                        assertTrue(
                            "The answer should be a failure response",
                            false
                        )
                    }
                    is Response.Loading -> {}
                    is Response.Failure -> {
                        assertTrue(
                            response.exception.message,
                            response.exception is InsightException.NoteNotFoundException
                        )
                    }
                }
            }
    }


    @Test
    fun whenDeleteNoteWithInvalidIdThrowsNoteNotFoundException() = runTest {
        populateDb()
        val noteFromDb = noteDao.getById(1)!!.copy(id = 100)
        viewModel.onEvent(InsightUiEvent.OnDeleteNote(noteFromDb))
        viewModel.uiState
            .map { it.deleteNoteState }
            .until { it is Response.Failure }
            .collectLatest { response ->
                when (response) {
                    is Response.Success -> {
                        assertTrue(
                            "The answer should be a failure response",
                            false
                        )
                    }
                    is Response.Loading -> {}
                    is Response.Failure -> {
                        assertTrue(
                            response.exception.message,
                            response.exception is InsightException.NoteNotFoundException
                        )
                    }
                }
            }
    }

    /** Fetch Notes By Creation Date in Ascending Order **/
    @Test
    fun whenFetchNotesByCreationDateInDescendingOrderIsSuccessful() = runTest {
        populateDb()
        val tag = tagDao.getById(1)!!
        viewModel.onEvent(InsightUiEvent.OnFetchNotesByCreationDataInDescendingOrder(tag.id))
        viewModel.uiState
            .map { it.fetchNotesState }
            .until { it is Response.Success }
            .collectLatest { response ->
                when(response){
                    is Response.Success -> {
                        val actualNotes = response.result
                        actualNotes.forEachIndexed { index, note ->
                            if(actualNotes.isNotEmpty() && index < actualNotes.size - 1) {
                                assertTrue(note.creationDate >= actualNotes[index + 1].creationDate)
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

    /** Fetch Notes By Tag in Descending Order **/
    @Test
    fun whenFetchNotesByTitleInAscendingOrderIsSuccessful() = runTest {
        populateDb()
        val tag = tagDao.getById(1)!!
        viewModel.onEvent(InsightUiEvent.OnFetchNotesByTitleInAscendingOrder(tag.id))
        viewModel.uiState
            .map { it.fetchNotesState }
            .until { it is Response.Success }
            .collectLatest { response ->
                when(response){
                    is Response.Success -> {
                        val actualNotes = response.result
                        actualNotes.forEachIndexed { index, note ->
                            if(actualNotes.isNotEmpty() && index < actualNotes.size - 1) {
                                assertTrue(note.title <= actualNotes[index + 1].title)
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

    /** Fetch Notes By Situation in Ascending Order **/
    @Test
    fun whenFetchNotesBySituationInAscendingOrderIsSuccessful() = runTest {
        populateDb()
        val tag = tagDao.getById(1)!!
        viewModel.onEvent(InsightUiEvent.OnFetchNotesBySituationInAscendingOrder(tag.id))
        viewModel.uiState
            .map { it.fetchNotesState }
            .until { it is Response.Success }
            .collectLatest { response ->
                when(response){
                    is Response.Success -> {
                        val actualNotes = response.result
                        actualNotes.forEachIndexed { index, note ->
                            if(actualNotes.isNotEmpty() && index < actualNotes.size - 1) {
                                assertTrue(note.situation <= actualNotes[index + 1].situation)
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

}