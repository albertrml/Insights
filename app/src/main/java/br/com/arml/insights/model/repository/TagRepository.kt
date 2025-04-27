package br.com.arml.insights.model.repository

import br.com.arml.insights.model.entity.Tag
import br.com.arml.insights.model.source.TagDao
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.exception.InsightException
import br.com.arml.insights.utils.tools.performDatabaseOperation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TagRepository @Inject constructor(private val tagDao: TagDao){

    fun insert(tag: Tag) = flow {
        emit(Response.Loading)
        emit(performDatabaseOperation { tagDao.insert(tag) })
    }

    fun delete(tag: Tag) = flow {
        emit(Response.Loading)
        emit(
            performDatabaseOperation {
                val tagFromDb = tagDao.getById(tag.id)
                tagFromDb?.let {
                    if(it != tag) throw InsightException.TagNotFoundException()
                } ?: throw InsightException.TagNotFoundException()
                tagDao.delete(tag)
            }
        )
    }

    fun update(tag: Tag) = flow {
        emit(Response.Loading)
        emit(performDatabaseOperation { tagDao.update(tag) })
    }

    fun getAll() : Flow<Response<List<Tag>>> = flow {
        emit(Response.Loading)
        try {
            emitAll(
                tagDao.getAll().map {
                    Response.Success(it)
                }
            )
        }catch (e: Exception){
            Response.Failure(e)
        }
    }

    suspend fun isTagNameExists(tag: String) = tagDao.isTagNameExists(tag)

    suspend fun getTagById(index: Int) = tagDao.getById(index)
}