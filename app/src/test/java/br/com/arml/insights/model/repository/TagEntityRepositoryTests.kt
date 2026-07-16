package br.com.arml.insights.model.repository

import android.database.sqlite.SQLiteConstraintException
import br.com.arml.insights.model.entity.TagEntity
import br.com.arml.insights.model.mock.mockTagEntities
import br.com.arml.insights.model.source.TagDao
import br.com.arml.core.response.Response
import br.com.arml.core.flow.until
import br.com.arml.insights.utils.exception.InsightException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Situation: Now, the new database model uses NoteTagLinkEntity to relate notes with tags,
 * allowing relation n:n between them. The tagId FK in the NoteEntity is not used anymore,
 * so tagId is removed from the NoteEntity and NoteUi.
 * TODO: rewrite them using TagWithNotes or NoteWithTags
 * **/
class TagEntityRepositoryTests {

    val tagDao = mockk<TagDao>()
    val tagRepository = TagRepository(tagDao)

    @Test
    fun `should emit success when Insert Tag Is Successful`() = runTest {
        mockTagEntities.forEach { fakeTag ->
            coEvery { tagDao.insert(fakeTag) } returns Unit
            tagRepository
                .insert(fakeTag)
                .until { response -> response is Response.Success<*> }
                .collect { response ->
                    when (response) {
                        is Response.Success<*> -> assertTrue(true)
                        is Response.Loading -> assertTrue(true)
                        is Response.Failure -> assertTrue(false)
                    }
                }

            coVerify(exactly = 1) {
                tagDao.insert(fakeTag)
            }
        }
    }

    @Test
    fun `should emit Failure when Insert Tag With Duplicated Id `() = runTest {
        /*val fakeTagWithDuplicatedId = mockTagEntities[0].copy(id = 1)
        val insertIds = mutableSetOf(1)
        val tagEntitySlot = slot<TagEntity>()

        coEvery { tagDao.insert(capture(tagEntitySlot)) } coAnswers {
            val insertedTag = tagEntitySlot.captured
            if (insertIds.contains(insertedTag.id)) {
                throw SQLiteConstraintException()
            } else {
                insertIds.add(insertedTag.id)
            }
        }

        tagRepository
            .insert(fakeTagWithDuplicatedId)
            .until { response -> response is Response.Failure }
            .collect { response ->
                when (response) {
                    is Response.Success<*> -> assertTrue("Should be Failure", false)
                    is Response.Loading -> assertTrue(true)
                    is Response.Failure -> {
                        assertTrue(response.exception is SQLiteConstraintException)
                    }
                }
            }*/
    }

    @Test
    fun `should emit Success when Insert Tags with Different Ids `() = runTest {
        val insertedTags = mutableSetOf(mockTagEntities[0].copy(id = 1))
        val tagEntitySlot = slot<TagEntity>()
        coEvery { tagDao.insert(capture(tagEntitySlot)) } coAnswers {
            val newTag = tagEntitySlot.captured
            if (insertedTags.contains(newTag)){
                throw SQLiteConstraintException()
            }else{
                insertedTags.add(newTag.copy(id = insertedTags.size+1L))
            }
        }

        tagRepository
            .insert(mockTagEntities[0])
            .until { response -> response is Response.Success<*> }
            .collect { response ->
                when (response) {
                    is Response.Success -> assertTrue(true)
                    is Response.Loading -> assertTrue(true)
                    is Response.Failure -> assertTrue(false)
                }
            }
    }

    @Test
    fun `should emit Success when Delete Tag Is Successful`() = runTest{
        val fakeTag = mockTagEntities[0].copy(id = 1)
        val database = mutableSetOf(fakeTag)
        val tagEntitySlot = slot<TagEntity>()

        // Mock do getById para retornar o fakeTag
        coEvery { tagDao.getById(fakeTag.id) } returns fakeTag

        coEvery { tagDao.delete(capture(tagEntitySlot)) } coAnswers {
            val deletedTag = tagEntitySlot.captured
            if (database.contains(deletedTag)) {
                database.remove(deletedTag)
            }
        }

        tagRepository
            .delete(fakeTag)
            .until { response -> response is Response.Success<*> }
            .collect { response ->
                when (response) {
                    is Response.Success<*> -> {
                        assertEquals(0, database.size)
                    }
                    is Response.Loading -> assertTrue(true)
                    is Response.Failure -> assertTrue(false)
                }
            }

    }

    @Test
    fun `should throw TagNotFoundException when Delete Tag Is Not Found`() = runTest{
        val fakeTag = mockTagEntities[0].copy(id = 1)

        // Mock do getById para retornar o fakeTag
        coEvery { tagDao.getById(fakeTag.id) } returns null

        tagRepository
            .delete(fakeTag)
            .until { response -> response is Response.Success<*> }
            .collect { response ->
                when (response) {
                    is Response.Success<*> -> assertTrue(false)
                    is Response.Loading -> assertTrue(true)
                    is Response.Failure -> {
                        assertTrue(response.exception is InsightException.TagNotFoundException)
                    }
                }
            }
    }

    @Test
    fun `should throw TagNotFoundException when Tag for delete Is The Same`() = runTest{
        val fakeTag = mockTagEntities[0].copy(id = 1)
        val tagForDelete = mockTagEntities[1].copy(id = 1)

        coEvery { tagDao.getById(tagForDelete.id) } returns fakeTag

        tagRepository
            .delete(tagForDelete)
            .until { response -> response is Response.Success<*> }
            .collect { response ->
                when (response) {
                    is Response.Success<*> -> assertTrue(false)
                    is Response.Loading -> assertTrue(true)
                    is Response.Failure -> {
                        assertTrue(response.exception is InsightException.TagNotFoundException)
                    }
                }
            }

    }

    @Test
    fun `should emit Success when Update Tag Is Successful`() = runTest{
        val fakeTag = mockTagEntities[0].copy(id = 1)
        val updatedTag = mockTagEntities[1].copy(id = 1)
        val database = mutableSetOf(fakeTag)

        coEvery { tagDao.update(updatedTag) } answers {
            database.remove(fakeTag)
            database.add(updatedTag)
        }

        tagRepository.update(updatedTag)
            .until { response -> response is Response.Success<*> }
            .collect { response ->
                when (response) {
                    is Response.Success<*> -> {
                        assertTrue(database.contains(updatedTag))
                        assertFalse(database.contains(fakeTag))
                    }
                    is Response.Loading -> assertTrue(true)
                    is Response.Failure -> assertTrue(false)
                }
            }
    }

    @Test
    fun `should emit Success when Get All Tags Is Successful`() = runTest{
        val database = mutableSetOf<TagEntity>()
        mockTagEntities.forEachIndexed { index, fakeTag ->
            database.add(fakeTag.copy(id = index+1L))
        }

        coEvery { tagDao.getAll() } returns flow { database.toList() }

        tagRepository
            .getAll()
            .until { response -> response is Response.Success<*> }
            .collectLatest { response ->
                when(response){
                    is Response.Success<*> -> {
                        assertEquals(database, response.result)
                    }
                    is Response.Loading -> assertTrue(true)
                    is Response.Failure -> assertTrue(false)
                }
            }

    }

    @Test
    fun `should emit Tag when It exists`() = runTest{
        val fakeTag = mockTagEntities[0].copy(id = 1)
        coEvery { tagDao.getById(fakeTag.id) } returns fakeTag
        val tag = tagRepository.getTagById(fakeTag.id)
        assertEquals(fakeTag, tag)
    }

    @Test
    fun `should emit null when It does not exists`() = runTest{
        val fakeTag = mockTagEntities[0].copy(id = 1)
        coEvery { tagDao.getById(fakeTag.id) } returns null
        val tag = tagRepository.getTagById(fakeTag.id)
        assertNull(tag)
    }
}