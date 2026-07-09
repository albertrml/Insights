package br.com.arml.insights.model.repository

import br.com.arml.core.response.asResponse
import br.com.arml.core.response.toResponseFlow
import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.source.TagDao
import br.com.arml.insights.utils.exception.InsightException
import javax.inject.Inject

class TagRepository @Inject constructor(private val tagDao: TagDao){

    fun insert(tag: Tag) = asResponse { tagDao.insert(tag) }

    fun delete(tag: Tag) = asResponse {
        val tagFromDb = tagDao.getById(tag.id)
        tagFromDb?.let {
            if(it != tag) throw InsightException.TagNotFoundException()
        } ?: throw InsightException.TagNotFoundException()
        tagDao.delete(tag)
    }

    fun update(tag: Tag) = asResponse { tagDao.update(tag) }

    fun getAll() = tagDao.getAll().toResponseFlow()

    suspend fun isTagNameExists(tag: String) = tagDao.isTagNameExists(tag)

    suspend fun getTagById(index: Int) = tagDao.getById(index)
}