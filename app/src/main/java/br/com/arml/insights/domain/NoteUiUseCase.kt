package br.com.arml.insights.domain

import br.com.arml.core.response.mapSuccess
import br.com.arml.insights.model.domain.NoteUi
import br.com.arml.insights.model.domain.TagUi
import br.com.arml.insights.model.repository.NoteRepository
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.utils.data.SearchNoteCategory
import br.com.arml.insights.utils.data.SortedNote
import br.com.arml.insights.utils.data.SortedTag
import br.com.arml.insights.utils.data.filterNotesBy
import br.com.arml.insights.utils.data.sortNotesBy
import br.com.arml.insights.utils.data.sortTagsBy
import javax.inject.Inject

class NoteUiUseCase @Inject constructor(
    private val tagRepository: TagRepository,
    private val noteRepository: NoteRepository
) {
    fun addNote(noteUi: NoteUi) = noteRepository.insert(noteUi.toNoteEntity())

    fun deleteNote(noteUi: NoteUi) = noteRepository.delete(noteUi.toNoteEntity())

    fun updateNote(noteUi: NoteUi) = noteRepository.update(noteUi.toNoteEntity())

    fun fetchNotesByTag(
        tagId: Long,
        sortedNote: SortedNote
    ) = noteRepository.getByTag(tagId)
        .mapSuccess { notes ->
            notes.sortNotesBy(sortedNote).map { NoteUi.fromNoteEntity(it) }
        }

    fun searchNotes(
        tagId: Long,
        query: String,
        searchNoteCategory: SearchNoteCategory
    ) = noteRepository.getByTag(tagId)
        .mapSuccess { notes ->
            notes.filterNotesBy(query, searchNoteCategory).map { NoteUi.fromNoteEntity(it) }
        }

    fun fetchTagUi(sortBy: SortedTag) = tagRepository.getAll()
        .mapSuccess { tags ->
            tags.sortTagsBy(sortBy).map { TagUi.fromTagEntity(it) }
        }
}