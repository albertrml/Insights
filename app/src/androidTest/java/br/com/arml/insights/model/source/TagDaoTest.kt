package br.com.arml.insights.model.source

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.mock.mockTags
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TagDaoTest {
    private lateinit var db: InsightsRoomDatabase
    private lateinit var tagDao: TagDao
    private val tags = mockTags

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context = context,
                klass = InsightsRoomDatabase::class.java
        ).build()
        tagDao = db.tagDao()
    }

    @Before
    fun clearTables() = runTest { db.clearAllTables() }

    @After
    fun closeDb() = runTest { db.close() }

    /*** Insert and Get Tags ***/
    @Test
    fun whenInsertTagsAndGetAllIsSuccessful() = runTest {
        tags.forEach { tag -> tagDao.insert(tag) }
        val allTags = tagDao.getAll().first().map { it.copy(id = 0) }
        assertEquals(tags, allTags)
    }

    @Test
    fun whenTryToGetTagsButDbIsEmpty() = runTest {
        val allTags = tagDao.getAll().first()
        assertEquals(emptyList<Tag>(), allTags)
    }

    @Test
    fun whenInsertDuplicateTagIdsThrowsSQLiteConstraintException() = runTest {
        val duplicateTags = tags.map { tag -> tag.copy(id = 1) }
        assertThrows(SQLiteConstraintException::class.java){
            runBlocking{ duplicateTags.forEach { tag -> tagDao.insert(tag) } }
        }
    }

    /*** Get Tag By Id ***/
    @Test
    fun whenGetTagByIdIsSuccessful() = runTest {
        tags.forEach { tag -> tagDao.insert(tag) }
        val expectedTag = tagDao.getAll().first().first()
        val actualTag = tagDao.getById(expectedTag.id)
        assertEquals(expectedTag, actualTag)
    }

    @Test
    fun whenGetTagByIdButTagDoesNotExist() = runTest {
        tags.forEach { tag -> tagDao.insert(tag) }
        val actualTag = tagDao.getById(100)
        assertNull(actualTag)
    }

    /*** Delete Tags ***/
    @Test
    fun whenDeleteTagAndGetAllIsSuccessful() = runTest {
        tags.forEach { tag -> tagDao.insert(tag) }
        val tagToDelete = tagDao.getAll().first().first()
        tagDao.delete(tagToDelete)
        val allTags = tagDao.getAll().first().map { it.copy(id = 0) }
        assertEquals(
            tags.size - 1,
            allTags.size
        )
        assertTrue(
            allTags.none { tag -> tag.id == tagToDelete.id }
        )
    }

    /*** Update Tags ***/
    @Test
    fun whenUpdateTagChangesAreSuccessful() = runTest {
        tags.forEach { tag -> tagDao.insert(tag) }
        val tagToUpdate = tagDao.getAll().first().first()
        val expectedTag = tagToUpdate.copy(name = "New Name")
        tagDao.update(expectedTag)
        val actualTag = tagDao.getById(expectedTag.id)

        assertEquals(expectedTag, actualTag)
        assertNotEquals(tagToUpdate, actualTag)
    }

    /*** Check If Tag Exists ***/
    @Test
    fun whenCheckIfTagExistsAndTagExists() = runTest {
        tags.forEach { tag -> tagDao.insert(tag) }
        val tagToCheck = tagDao.getAll().first().first()
        val tagExists = tagDao.isTagNameExists(tagToCheck.name)
        assertTrue(tagExists)
    }

    @Test
    fun whenCheckIfTagExistsAndTagDoesNotExist() = runTest {
        tags.forEach { tag -> tagDao.insert(tag) }
        val tagExists = tagDao.isTagNameExists("New Tag")
        assertFalse(tagExists)
    }

}