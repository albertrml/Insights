package br.com.arml.insights.model.viewmodel

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.model.source.InsightsRoomDatabase
import br.com.arml.insights.model.source.TagDao
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.tools.until
import br.com.arml.insights.viewmodel.updatetag.UpdateTagUiEvent
import br.com.arml.insights.viewmodel.updatetag.UpdateTagViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UpdateTagViewModelTest {

    private lateinit var viewModel: UpdateTagViewModel
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
        viewModel = UpdateTagViewModel(tagRepository)
    }

    @Before
    fun clearTables() = db.clearAllTables()

    @After
    fun closeDb() = db.close()

    private suspend fun populateDb() {
        tags.forEach { tag -> tagDao.insert(tag) }
    }

    /** Update Tag **/
    @Test
    fun whenUpdateTagIsSuccessful() = runTest {
        populateDb()
        val tag = tagDao.getById(1)!!.copy(
            name = "New Name",
            description = "New Description",
            color = 0xFFFFFF00
        )

        viewModel.onEvent(
            UpdateTagUiEvent.OnUpdateTag(
                tagId = tag.id,
                newName = tag.name,
                newDescription = tag.description,
                newColor = tag.color
            )
        )

        viewModel.uiState
            .map { it.updateTagState }
            .until { it is Response.Success }
            .collectLatest { response ->
                when(response){
                    is Response.Success -> {
                        val actualTag = tagDao.getById(tag.id)!!
                        assertEquals(tag.name,actualTag.name)
                        assertEquals(tag.description,actualTag.description)
                        assertEquals(tag.color,actualTag.color)
                    }
                    is Response.Loading -> {}
                    is Response.Failure -> {
                        assertTrue(false)
                    }
                }
            }
    }

}