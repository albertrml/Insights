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
import br.com.arml.insights.viewmodel.updateinsight.UpdateInsightViewModel
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before

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

    suspend fun populateDb() {
        tags.forEach { tag -> tagDao.insert(tag) }

        val allTags = tagDao.getAll().first()
        allTags.forEach { tag -> notes.addMockNotes(tag, 5) }

        notes.forEach { note -> noteDao.insert(note) }
    }

    /** Fetch Insight **/

    /** Fetch Tags **/

    /** Update Insight **/

}