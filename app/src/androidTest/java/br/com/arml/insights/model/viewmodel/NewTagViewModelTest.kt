package br.com.arml.insights.model.viewmodel

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.model.source.InsightsRoomDatabase
import br.com.arml.insights.model.source.TagDao
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.exception.InsightException
import br.com.arml.insights.utils.exception.InsightException.TagAlreadyExistsException
import br.com.arml.insights.utils.tools.until
import br.com.arml.insights.viewmodel.newtag.NewTagUiEvent
import br.com.arml.insights.viewmodel.newtag.NewTagViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

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

    /** Insert Tag **/
    @Test
    fun whenInsertTagIsSuccessful() = runTest {
        val tag = tags.first
        viewModel.onEvent(NewTagUiEvent
            .OnNewTag(name = tag.name, color = tag.color, description = tag.description)
        )

        viewModel.uiState
            .map { it.insertTagState }
            .until { it is Response.Success }
            .collect{ response ->
                when(response){
                    is Response.Success -> {
                        val actualTag = tagDao.getById(1)
                        assertTrue(actualTag!!.name == tag.name)
                        assertTrue(actualTag.color == tag.color)
                        assertTrue(actualTag.description == tag.description)
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
    fun whenInsertDuplicatedTagNameIsFailure() = runTest {
        tagDao.insert(tags.first)
        val tag = tagDao.getById(1)!!
        viewModel.onEvent(NewTagUiEvent
            .OnNewTag(name = tag.name, color = tag.color, description = tag.description)
        )
        viewModel.uiState
            .map { it.insertTagState }
            .until { it is Response.Failure }
            .collect{ response ->
                when(response){
                    is Response.Success -> {
                        assertTrue(
                            "The answer should be a Failure response",
                            false
                        )
                    }
                    is Response.Loading -> {}
                    is Response.Failure -> {
                        assertTrue(
                            response.exception.message,
                            response.exception is TagAlreadyExistsException
                        )
                    }
                }
            }
    }
}