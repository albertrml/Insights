package br.com.arml.insights.model.viewmodel

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.model.source.InsightsRoomDatabase
import br.com.arml.insights.model.source.TagDao
import br.com.arml.insights.viewmodel.home.HomeViewModel
import org.junit.After
import org.junit.Before

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: TagRepository
    private lateinit var dao: TagDao
    private lateinit var db: InsightsRoomDatabase
    private val tags = mockTags

    @Before
    fun setupDb(){
        val ctx = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context = ctx,
            klass = InsightsRoomDatabase::class.java
        ).build()

        dao = db.tagDao()
        repository = TagRepository(dao)
        viewModel = HomeViewModel(repository)
    }

    @Before
    fun clearTables() { db.clearAllTables() }

    @After
    fun closeDb() { db.close() }

    suspend fun populateDb() { tags.forEach { tag -> dao.insert(tag) } }

    /** Delete Tag  **/

    /** Fetch tags by color in ascending order  **/

    /** Fetch tags by name in ascending order **/

}