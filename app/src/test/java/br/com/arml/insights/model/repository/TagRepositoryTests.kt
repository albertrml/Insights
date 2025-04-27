package br.com.arml.insights.model.repository

import android.database.sqlite.SQLiteConstraintException
import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.mock.mockTags
import br.com.arml.insights.model.source.TagDao
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.exception.InsightException
import br.com.arml.insights.utils.tools.until
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

class TagRepositoryTests {

    val tagDao = mockk<TagDao>()
    val tagRepository = TagRepository(tagDao)

    @Test
    fun `should emit success when Insert Tag Is Successful`() = runTest {
        mockTags.forEachIndexed { index, fakeTag ->
            coEvery { tagDao.insert(fakeTag) } returns Unit
            tagRepository
                .insert(fakeTag)
                .until { response -> response is Response.Success }
                .collect { response ->
                    when (response) {
                        is Response.Success -> assertTrue(true)
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
        val fakeTagWithDuplicatedId = mockTags[0].copy(id = 1)
        val insertIds = mutableSetOf(1) // Simulando que o ID 1 já existe
        val tagSlot = slot<Tag>()       // Slot para capturar a Tag inserida

        coEvery { tagDao.insert(capture(tagSlot)) } coAnswers {
            val insertedTag = tagSlot.captured
            if (insertIds.contains(insertedTag.id)) {
                throw SQLiteConstraintException()
            } else {
                insertIds.add(insertedTag.id)
                Unit
            }
        }

        tagRepository
            .insert(fakeTagWithDuplicatedId)
            .until { response -> response is Response.Failure }
            .collect { response ->
                when (response) {
                    is Response.Success -> assertTrue("Should be Failure", false)
                    is Response.Loading -> assertTrue(true)
                    is Response.Failure -> {
                        assertTrue(response.exception is SQLiteConstraintException)
                    }
                }
            }
    }

    @Test
    fun `should emit Success when Insert Tags with Different Ids `() = runTest {
        val insertedTags = mutableSetOf<Tag>(mockTags[0].copy(id = 1))
        val tagSlot = slot<Tag>()
        coEvery { tagDao.insert(capture(tagSlot)) } coAnswers {
            val newTag = tagSlot.captured
            if (insertedTags.contains(newTag)){
                throw SQLiteConstraintException()
            }else{
                insertedTags.add(newTag.copy(id = insertedTags.size+1))
                Unit
            }
        }

        tagRepository
            .insert(mockTags[0])
            .until { response -> response is Response.Success }
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
        val fakeTag = mockTags[0].copy(id = 1)
        val database = mutableSetOf(fakeTag)
        val tagSlot = slot<Tag>()

        // Mock do getById para retornar o fakeTag
        coEvery { tagDao.getById(fakeTag.id) } returns fakeTag

        coEvery { tagDao.delete(capture(tagSlot)) } coAnswers {
            val deletedTag = tagSlot.captured
            if (database.contains(deletedTag)) {
                database.remove(deletedTag)
            }
            Unit // <<< Fora do if
        }

        tagRepository
            .delete(fakeTag)
            .until { response -> response is Response.Success }
            .collect { response ->
                when (response) {
                    is Response.Success -> {
                        assertEquals(0, database.size)
                    }
                    is Response.Loading -> assertTrue(true)
                    is Response.Failure -> assertTrue(false)
                }
            }

    }

    @Test
    fun `should throw TagNotFoundException when Delete Tag Is Not Found`() = runTest{
        val fakeTag = mockTags[0].copy(id = 1)

        // Mock do getById para retornar o fakeTag
        coEvery { tagDao.getById(fakeTag.id) } returns null

        tagRepository
            .delete(fakeTag)
            .until { response -> response is Response.Success }
            .collect { response ->
                when (response) {
                    is Response.Success -> assertTrue(false)
                    is Response.Loading -> assertTrue(true)
                    is Response.Failure -> {
                        assertTrue(response.exception is InsightException.TagNotFoundException)
                    }
                }
            }
    }

    @Test
    fun `should throw TagNotFoundException when Tag for delete Is The Same`() = runTest{
        val fakeTag = mockTags[0].copy(id = 1)
        val tagForDelete = mockTags[1].copy(id = 1)

        coEvery { tagDao.getById(tagForDelete.id) } returns fakeTag

        tagRepository
            .delete(tagForDelete)
            .until { response -> response is Response.Success }
            .collect { response ->
                when (response) {
                    is Response.Success -> assertTrue(false)
                    is Response.Loading -> assertTrue(true)
                    is Response.Failure -> {
                        assertTrue(response.exception is InsightException.TagNotFoundException)
                    }
                }
            }

    }

    @Test
    fun `should emit Success when Update Tag Is Successful`() = runTest{
        val fakeTag = mockTags[0].copy(id = 1)
        val updatedTag = mockTags[1].copy(id = 1)
        val database = mutableSetOf(fakeTag)

        coEvery { tagDao.update(updatedTag) } answers {
            database.remove(fakeTag)
            database.add(updatedTag)
            Unit
        }

        tagRepository.update(updatedTag)
            .until { response -> response is Response.Success }
            .collect { response ->
                when (response) {
                    is Response.Success -> {
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
        val database = mutableSetOf<Tag>()
        mockTags.forEachIndexed { index, fakeTag ->
            database.add(fakeTag.copy(id = index+1))
        }

        coEvery { tagDao.getAll() } returns flow<List<Tag>> { database.toList() }

        tagRepository
            .getAll()
            .until { response -> response is Response.Success }
            .collectLatest { response ->
                when(response){
                    is Response.Success -> {
                        assertEquals(database, response.result)
                    }
                    is Response.Loading -> assertTrue(true)
                    is Response.Failure -> assertTrue(false)
                }
            }

    }

    @Test
    fun `should emit Tag when It exists`() = runTest{
        val fakeTag = mockTags[0].copy(id = 1)
        coEvery { tagDao.getById(fakeTag.id) } returns fakeTag
        val tag = tagRepository.getTagById(fakeTag.id)
        assertEquals(fakeTag, tag)
    }

    @Test
    fun `should emit null when It does not exists`() = runTest{
        val fakeTag = mockTags[0].copy(id = 1)
        coEvery { tagDao.getById(fakeTag.id) } returns null
        val tag = tagRepository.getTagById(fakeTag.id)
        assertNull(tag)
    }
}