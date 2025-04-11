package br.com.arml.insights.model.viewmodel

import android.content.Context
import android.database.sqlite.SQLiteException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.arml.insights.domain.TagUiUseCase
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.model.source.InsightsRoomDatabase
import br.com.arml.insights.model.source.TagDao
import br.com.arml.insights.ui.screen.tag.TagEvent
import br.com.arml.insights.ui.screen.tag.TagViewModel
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.exception.InsightException.TagAlreadyExistsException
import br.com.arml.insights.utils.exception.InsightException.TagNotFoundException
import br.com.arml.insights.utils.tools.until
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class TagUiViewModelTest {

    private lateinit var viewModel: TagViewModel
    private lateinit var useCase: TagUiUseCase
    private lateinit var repository: TagRepository
    private lateinit var dao: TagDao
    private lateinit var db: InsightsRoomDatabase
    private val tags = mockTags
    private val tagsUi = mockTags.map { TagUi.fromTag(it) }

    @Before
    fun setupDb() {
        val ctx = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context = ctx,
            klass = InsightsRoomDatabase::class.java
        ).build()

        dao = db.tagDao()
        repository = TagRepository(dao)
        useCase = TagUiUseCase(repository)
        viewModel = TagViewModel(useCase)
    }

    @Before
    fun clearTables() {
        db.clearAllTables()
    }

    @After
    fun closeDb() {
        db.close()
    }

    private suspend fun populateDb() {
        tags.forEach { tag -> dao.insert(tag) }
    }


    /** Delete Tag  **/
    @Test
    fun whenDeleteTagIsSuccessful() = runTest {
        val index = 4
        populateDb()
        val tagFromDb = dao.getById(index)!!
        val tagUiFromDb = TagUi.fromTag(tagFromDb)
        viewModel.onEvent(TagEvent.OnDeleteTag(tagUiFromDb))
        viewModel.uiState
            .map { it.deleteState }
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
        val tagFromDb = dao.getById(1)!!.copy( id = 2)
        val tagUiFromDb = TagUi.fromTag(tagFromDb)
        viewModel.onEvent(TagEvent.OnDeleteTag(tagUiFromDb))
        viewModel.uiState
            .map { it.deleteState }
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
    fun whenFetchTagsByNameInAscendingOrderIsSuccessful() = runTest {
        populateDb()
        viewModel.onEvent(TagEvent.OnRetrieveTag)
        viewModel.uiState
            .map { it.retrieveState }
            .until { it is Response.Success }
            .collectLatest{ response ->
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

    /** Insert Tag **/
    @Test
    fun whenInsertTagIsSuccessful() = runTest {
        val expectedTagUi = tagsUi.first()
        viewModel.onEvent(TagEvent.OnInsertTag(expectedTagUi))

        viewModel.uiState
            .map { it.insertState }
            .until { it is Response.Success }
            .collect{ response ->
                when(response){
                    is Response.Success -> {
                        val actualTagUi = TagUi.fromTag(dao.getById(1)).copy(id = 0)
                        assertTrue(actualTagUi == expectedTagUi)
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
    fun whenInsertDuplicatedTagIdIsFailure() = runTest {
        dao.insert(tags.first())
        val duplicatedTagUi = TagUi.fromTag(dao.getById(1)).copy(name = "")
        viewModel.onEvent(TagEvent.OnInsertTag(duplicatedTagUi))
        viewModel.uiState
            .map { it.insertState }
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
                            response.exception is SQLiteException
                        )
                    }
                }
            }
    }

    @Test
    fun whenInsertDuplicatedTagNameIsFailure() = runTest {
        dao.insert(tags.first())
        val duplicatedTagUi = TagUi.fromTag(dao.getById(1)).copy(description = "", id = 0)
        viewModel.onEvent(TagEvent.OnInsertTag(duplicatedTagUi))
        viewModel.uiState
            .map { it.insertState }
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

    /** Update Tag **/
    @Test
    fun whenUpdateTagIsSuccessful() = runTest {
        populateDb()
        val tagFromDb = dao.getById(1)!!.copy(
            name = "New Name",
            description = "New Description",
            color = 0xFFFFFF00
        )
        val expectedTagUi = TagUi.fromTag(tagFromDb)

        viewModel.onEvent(TagEvent.OnEditTag(expectedTagUi))

        viewModel.uiState
            .map { it.editState }
            .until { it is Response.Success }
            .collectLatest { response ->
                when(response){
                    is Response.Success -> {
                        val actualTagFromDb = dao.getById(tagFromDb.id)!!
                        val actualTagUi = TagUi.fromTag(actualTagFromDb)
                        assertEquals(expectedTagUi,actualTagUi)
                    }
                    is Response.Loading -> {}
                    is Response.Failure -> {
                        assertTrue(false)
                    }
                }
            }
    }

    /** Search Tags **/
    /*@Test
    fun whenSearchTagByNameIsSuccessful() = runTest {
        populateDb()
        val tagName = "dinner"
        viewModel.onEvent(TagUiEvent.OnSearchTagsUi(tagName))

        viewModel.uiState
            .map { it.searchState }
            .take(4)
            .collectLatest { tagsUi ->
                assertEquals(2, tagsUi.size)
                val names = tagsUi.map { it.name }
                assertTrue(names.contains("Dinner"))
                assertTrue(names.contains("Dinheiro"))
            }
    }*/
}