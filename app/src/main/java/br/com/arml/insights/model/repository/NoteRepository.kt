package br.com.arml.insights.model.repository

import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.source.NoteDao
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.exception.InsightException
import br.com.arml.insights.utils.tools.performDatabaseOperation
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao){

    fun insert(note: Note) = flow{
        emit(Response.Loading)
        emit(performDatabaseOperation { noteDao.insert(note) })
    }

    fun delete(note: Note) = flow{
        emit(Response.Loading)
        emit(
            performDatabaseOperation {
                val noteFromDb = noteDao.getById(note.id)
                noteFromDb ?: throw InsightException.NoteNotFoundException()
                if (noteFromDb != note) throw InsightException.NoteNotFoundException()

                noteDao.delete(note)
            }
        )
    }

    fun update(note: Note) = flow{
        emit(Response.Loading)
        emit(performDatabaseOperation { noteDao.update(note) })
    }

    fun getAll() = flow {
        emit(Response.Loading)
        try {
            emitAll(
                noteDao.getAll().map {
                    Response.Success(it)
                }
            )
        }catch (e: Exception){
            Response.Failure(e)
        }
    }

    fun getByTag(tagId: Int) = flow {
        emit(Response.Loading)
        try {
            emitAll(
                noteDao.getByTag(tagId).map {
                    Response.Success(it)
                }
            )
        }catch (e: Exception){
            Response.Failure(e)
        }
    }

    fun getById(id: Int) = flow {
        emit(Response.Loading)
        emit(
            performDatabaseOperation {
                noteDao.getById(id) ?: throw InsightException.NoteNotFoundException()
            }
        )
    }
}