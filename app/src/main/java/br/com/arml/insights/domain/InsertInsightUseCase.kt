package br.com.arml.insights.domain

import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.repository.NoteRepository
import br.com.arml.insights.model.repository.TagRepository
import javax.inject.Inject

class InsertInsightUseCase @Inject constructor(
    private val tagRepository: TagRepository,
    private val noteRepository: NoteRepository
){
    suspend fun getTags() = tagRepository.getAll()

    fun insertNote(note: Note) = noteRepository.insert(note)

}