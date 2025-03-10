package br.com.arml.insights.model

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.source.InsightsRoomDatabase
import br.com.arml.insights.model.source.TagDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
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

    /*** Get Tag ***/

    /*** Check If Tag Exists ***/

}