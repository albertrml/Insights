package br.com.arml.insights.model.viewmodel

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.model.source.InsightsRoomDatabase
import br.com.arml.insights.model.source.TagDao
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.exception.InsightException.TagNotFoundException
import br.com.arml.insights.utils.tools.until
import br.com.arml.insights.viewmodel.home.HomeUiEvent
import br.com.arml.insights.viewmodel.home.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

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
    fun clearTables() {
        db.clearAllTables()
    }

    @After
    fun closeDb() { db.close() }

    private suspend fun populateDb() { tags.forEach { tag -> dao.insert(tag) } }

    /** Delete Tag  **/
    @Test
    fun whenDeleteTagIsSuccessful() = runTest {
        populateDb()
        val tagFromDb = dao.getById(1)!!
        viewModel.onEvent(HomeUiEvent.OnDeleteTag(tagFromDb))
        viewModel.uiState
            .map { it.deleteTagState }
            .until { it is Response.Success }
            .collect{ response ->
                when(response){
                    is Response.Success -> assertNull(dao.getById(tagFromDb.id))
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
    fun whenDeleteTagWithWrongId() = runTest {
        populateDb()
        val wrongTagWithValidId = dao.getById(1)!!.copy( id = 2)
        viewModel.onEvent(HomeUiEvent.OnDeleteTag(wrongTagWithValidId))
        viewModel.uiState
            .map { it.deleteTagState }
            .until { it is Response.Failure }
            .collect{ response ->
                when(response){
                    is Response.Success -> assertTrue(
                        "The answer should be a failure response",
                        false
                    )
                    is Response.Loading -> {}
                    is Response.Failure -> {
                        assertTrue(
                            response.exception.message,
                            response.exception is TagNotFoundException
                        )
                    }
                }
            }
    }

    /** Fetch tags by color in ascending order  **/
    @Test
    fun whenFetchTagsByColorInAscendingOrderIsSuccessful() = runTest {
        tags.forEach { tag ->
            repository.insert(tag)
                .filterIsInstance<Response.Success<Unit>>()
                .collect{ _ ->
                    viewModel.onEvent(HomeUiEvent.OnFetchTagsByColorInAscendingOrder)
                }
        }

        viewModel.uiState
            .map { it.fetchTagsState }
            .until { it is Response.Success }
            .collectLatest{ response ->
                when(response){
                    is Response.Success -> {
                        val actualTags = response.result
                        actualTags.forEachIndexed { index, tag ->
                            if(index < actualTags.size - 1) {
                                assertTrue(tag.color <= actualTags[index + 1].color)
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

    /** Fetch tags by name in ascending order **/
    @Test
    fun whenFetchTagsByNameInAscendingOrderIsSuccessful() = runTest {
        tags.forEach { tag ->
            repository.insert(tag)
                .filterIsInstance<Response.Success<Unit>>()
                .collect{ _ ->
                    viewModel.onEvent(HomeUiEvent.OnFetchTagsByNameInAscendingOrder)
                }
        }

        viewModel.uiState
            .map { it.fetchTagsState }
            .until { it is Response.Success }
            .collect{ response ->
                when(response){
                    is Response.Success -> {
                        val actualTags = response.result
                        actualTags.forEachIndexed { index, tag ->
                            if(index < actualTags.size - 1) {
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

}