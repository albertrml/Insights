package br.com.arml.insights.model.repository

import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.source.NoteDao
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.tools.performDatabaseOperation
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao){

    fun save(note: Note) = performDatabaseOperation { noteDao.insert(note) }

    fun delete(note: Note) = performDatabaseOperation { noteDao.delete(note) }

    fun update(note: Note) = performDatabaseOperation { noteDao.update(note) }

    fun getNotes() = flow {
        emit(Response.Loading)
        try {
            emitAll(noteDao.getAll().map { Response.Success(it) })
        }
        catch (e:Exception){
            emit(Response.Failure(e))
        }
    }

    fun getNote(tag: String) = performDatabaseOperation { noteDao.getByTag(tag) }

}