package br.com.arml.insights.model.repository

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.model.source.InsightsRoomDatabase
import br.com.arml.insights.model.source.TagDao
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.exception.InsightException.TagNotFoundException
import br.com.arml.insights.utils.tools.until
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TagRepositoryTest {

    private lateinit var tagRepository: TagRepository
    private lateinit var tagDao: TagDao
    private lateinit var db: InsightsRoomDatabase
    private val tags = mockTags

    @Before
    fun setupDb(){
        val ctx = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context = ctx,
            klass = InsightsRoomDatabase::class.java
        ).build()
        tagDao = db.tagDao()
        tagRepository = TagRepository(tagDao)
    }

    @Before
    fun clearDb() = db.clearAllTables()

    @After
    fun closeDb() = db.close()

    private suspend fun populateWithTags() {
        tags.forEach { tag -> tagDao.insert(tag) }
    }

    /** Insert Tags and Get All Tags **/
    @Test
    fun whenInsertTagsAndGetAllIsSuccessful() = runTest{
        combine(
            tagRepository.insert(tags.first),
            tagRepository.getAll()
        ) { insertTag, getAllTags ->
            if (insertTag is Response.Success && getAllTags is Response.Success) {
                val expectedTags = getAllTags.result.map { tag -> tag.copy(id = 0) }
                assertEquals(1, expectedTags.size)
                assertEquals(tags.first, expectedTags.first())
            }
            getAllTags
        }
            .until { it is Response.Success }
            .collect()
    }

    @Test
    fun whenTryToGetTagsButDbIsEmpty() = runTest {
        tagRepository.getAll()
            .until { it is Response.Success }
            .collect{ response ->
                when(response) {
                    is Response.Success -> {
                        assertEquals(emptyList<Tag>(), response.result)
                    }
                    is Response.Loading -> {}
                    is Response.Failure -> {
                        assertFalse(
                            "It should be a success response",
                            false
                        )
                    }
                }
            }
    }

    @Test
    fun whenInsertDuplicateTagIdsThrowsSQLiteConstraintException() = runTest {
        tagDao.insert(tags.first)
        val duplicateTag = tagDao.getById(1)!!

        tagRepository.insert(duplicateTag)
            .until { it is Response.Failure }
            .collect{ response ->
                when(response) {
                    is Response.Success -> {
                        assertFalse(
                            "It should be a failure response",
                            true
                        )
                    }
                    is Response.Loading -> {}
                    is Response.Failure -> {
                        assertTrue(
                            response.exception is SQLiteConstraintException
                        )
                    }
                }
            }

    }

    /** Get Tag By Id **/
    @Test
    fun whenGetTagByIdIsSuccessful() = runTest {
        populateWithTags()
        val expectedTag = tagDao.getById(1)!!
        tagRepository.getTagById(1)
            .until { it is Response.Success }
            .collect{ response ->
                when(response){
                    is Response.Success -> {
                        val actualTag = response.result
                        assertEquals(expectedTag, actualTag)
                    }
                    is Response.Loading -> {}
                    is Response.Failure -> {
                        assertFalse(
                            "It should be a success response",
                            false
                        )
                    }
                }
            }
    }

    @Test
    fun whenGetTagByIdButTagDoesNotExist() = runTest {
        tagRepository.getTagById(1)
            .until { it is Response.Failure }
            .collect{ response ->
                when(response){
                    is Response.Success -> {
                        assertFalse(true)
                        }
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

    /** Update Tag **/
    @Test
    fun whenUpdateTagChangesAreSuccessful() = runTest {
        populateWithTags()
        val notUpdatedTag = tagDao.getById(1)!!
        val updatedTag = notUpdatedTag.copy(name = "Updated")
        tagRepository.update(updatedTag)
            .until { it is Response.Success }
            .collect{ response ->
                when(response){
                    is Response.Success -> {
                        val actualTag = tagDao.getById(1)!!
                        assertNotEquals(notUpdatedTag, actualTag)
                        assertEquals(updatedTag, actualTag)
                    }
                    is Response.Loading -> {}
                    is Response.Failure -> {
                        assertFalse(
                            "It should be a success response",
                            true
                        )
                    }
                }
            }
    }

    /** Delete Tag **/
    @Test
    fun whenDeleteTagAndGetAllIsSuccessful() = runTest {
        populateWithTags()
        val tagToDelete = tagDao.getById(1)!!
        tagRepository.delete(tagToDelete)
            .until { it is Response.Success }
            .collect{ response ->
                when(response){
                    is Response.Success -> {
                        val getAllTags = tagDao.getAll().first()
                        assertTrue(getAllTags.size < tags.size)
                        assertNull(tagDao.getById(1))
                    }
                    is Response.Loading -> {}
                    is Response.Failure -> {
                        assertFalse(
                            "It should be a success response",
                            true
                        )
                    }
                }
            }

    }

}