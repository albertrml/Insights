package br.com.arml.insights.model.repository

import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.source.NoteDao
import br.com.arml.insights.utils.exception.InsightException
import br.com.arml.insights.utils.tools.performDatabaseOperation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class NoteRepository @Inject constructor(private val noteDao: NoteDao){

    fun insert(note: Note) = performDatabaseOperation { noteDao.insert(note) }

    fun delete(note: Note) = performDatabaseOperation { noteDao.delete(note) }

    fun update(note: Note) = performDatabaseOperation { noteDao.update(note) }

    fun getAll() = noteDao.getAll().flatMapConcat { performDatabaseOperation { it } }

    fun getByTag(tagId: Int) = noteDao.getByTag(tagId)
        .flatMapConcat { performDatabaseOperation { it } }

    fun getById(id: Int) = performDatabaseOperation {
        noteDao.getById(id) ?: throw InsightException.NoteNotFoundException()
    }
}