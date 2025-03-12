package br.com.arml.insights.model.viewmodel

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.model.source.InsightsRoomDatabase
import br.com.arml.insights.model.source.TagDao
import br.com.arml.insights.viewmodel.newtag.NewTagViewModel
import org.junit.After
import org.junit.Before

class NewTagViewModelTest {

    private lateinit var viewModel: NewTagViewModel
    private lateinit var tagRepository: TagRepository
    private lateinit var tagDao: TagDao
    private lateinit var db: InsightsRoomDatabase
    private val tags = mockTags

    @Before
    fun setupDb() {
        db = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            klass = InsightsRoomDatabase::class.java
        ).build()

        tagDao = db.tagDao()
        tagRepository = TagRepository(tagDao)
        viewModel = NewTagViewModel(tagRepository)
    }

    @Before
    fun clearTables() = db.clearAllTables()

    @After
    fun closeDb() = db.close()

    suspend fun populateDb() {
        tags.forEach { tag -> tagDao.insert(tag) }
    }

    /** Insert Tag **/

}