package br.com.arml.insights.model.repository

import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.source.TagDao
import br.com.arml.insights.utils.tools.performDatabaseOperation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

class TagRepository @Inject constructor(private val tagDao: TagDao){

    fun insert(tag: Tag) = performDatabaseOperation { tagDao.insert(tag) }

    fun delete(tag: Tag) = performDatabaseOperation { tagDao.delete(tag) }

    fun update(tag: Tag) = performDatabaseOperation { tagDao.update(tag) }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAll() = tagDao.getAll().flatMapConcat { performDatabaseOperation { it } }

    suspend fun isTagExists(tag: String) = tagDao.isTagExists(tag)

}