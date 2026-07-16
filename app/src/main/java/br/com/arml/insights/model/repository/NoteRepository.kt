package br.com.arml.insights.model.repository

import br.com.arml.core.response.Response
import br.com.arml.core.response.asResponse
import br.com.arml.core.response.toResponseFlow
import br.com.arml.insights.model.entity.NoteEntity
import br.com.arml.insights.model.source.NoteDao
import br.com.arml.insights.utils.exception.InsightException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao){

    /** CREATE **/
    fun insert(noteEntity: NoteEntity) = asResponse { noteDao.insert(noteEntity) }

    /** READ **/
    fun getAll(): Flow<Response<List<NoteEntity>>> = noteDao.getAll().toResponseFlow()

    fun getByTag(tagId: Long) = noteDao.getByTag(tagId).toResponseFlow()

    fun getById(id: Long) = asResponse {
        noteDao.getById(id) ?: throw InsightException.NoteNotFoundException()
    }

    /** UPDATE **/
    fun update(noteEntity: NoteEntity) = asResponse { noteDao.update(noteEntity) }

    /** DELETE **/
    fun delete(noteEntity: NoteEntity) = asResponse {
        val noteFromDb = noteDao.getById(noteEntity.id)
        noteFromDb ?: throw InsightException.NoteNotFoundException()
        if (noteFromDb != noteEntity) throw InsightException.NoteNotFoundException()

        noteDao.delete(noteEntity)
    }
}