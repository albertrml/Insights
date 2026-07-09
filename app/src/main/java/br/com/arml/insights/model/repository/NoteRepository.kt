package br.com.arml.insights.model.repository

import br.com.arml.core.response.asResponse
import br.com.arml.core.response.toResponseFlow
import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.source.NoteDao
import br.com.arml.insights.utils.exception.InsightException
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao){

    fun insert(note: Note) = asResponse { noteDao.insert(note) }

    fun delete(note: Note) = asResponse {
        val noteFromDb = noteDao.getById(note.id)
        noteFromDb ?: throw InsightException.NoteNotFoundException()
        if (noteFromDb != note) throw InsightException.NoteNotFoundException()

        noteDao.delete(note)
    }

    fun update(note: Note) = asResponse { noteDao.update(note) }

    fun getAll() = noteDao.getAll().toResponseFlow()

    fun getByTag(tagId: Int) = noteDao.getByTag(tagId).toResponseFlow()

    fun getById(id: Int) = asResponse {
        noteDao.getById(id) ?: throw InsightException.NoteNotFoundException()
    }
}