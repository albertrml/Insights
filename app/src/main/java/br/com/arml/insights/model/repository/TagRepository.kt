package br.com.arml.insights.model.repository

import br.com.arml.core.response.asResponse
import br.com.arml.core.response.toResponseFlow
import br.com.arml.insights.model.entity.TagEntity
import br.com.arml.insights.model.source.TagDao
import br.com.arml.insights.utils.exception.InsightException
import javax.inject.Inject

class TagRepository @Inject constructor(private val tagDao: TagDao){

    fun insert(tagEntity: TagEntity) = asResponse { tagDao.insert(tagEntity) }

    fun delete(tagEntity: TagEntity) = asResponse {
        val tagFromDb = tagDao.getById(tagEntity.id)
        tagFromDb?.let {
            if(it != tagEntity) throw InsightException.TagNotFoundException()
        } ?: throw InsightException.TagNotFoundException()
        tagDao.delete(tagEntity)
    }

    fun update(tagEntity: TagEntity) = asResponse { tagDao.update(tagEntity) }

    fun getAll() = tagDao.getAll().toResponseFlow()

    suspend fun isTagNameExists(tag: String) = tagDao.isTagNameExists(tag)

    suspend fun getTagById(index: Long) = tagDao.getById(index)
}