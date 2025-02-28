package br.com.arml.insights.model.repository

import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.source.TagDao
import br.com.arml.insights.utils.tools.performDatabaseOperation
import javax.inject.Inject

class TagRepository @Inject constructor(private val tagDao: TagDao){

    fun create(tag: Tag) = performDatabaseOperation { tagDao.insert(tag) }

    fun update(tag: Tag) = performDatabaseOperation { tagDao.update(tag) }

    fun delete(tag: Tag) = performDatabaseOperation { tagDao.delete(tag) }

    suspend fun getAll() = tagDao.getAll()

    suspend fun isTagExists(tag: String) = tagDao.isTagExists(tag)

}