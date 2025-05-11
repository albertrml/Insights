package br.com.arml.insights.domain

import br.com.arml.insights.model.entity.NoteUi
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.model.repository.NoteRepository
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.utils.data.SearchNoteCategory
import br.com.arml.insights.utils.data.SortedNote
import br.com.arml.insights.utils.data.SortedTag
import br.com.arml.insights.utils.data.filterNotesBy
import br.com.arml.insights.utils.data.mapTo
import br.com.arml.insights.utils.data.sortNotesBy
import br.com.arml.insights.utils.data.sortTagsBy
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteUiUseCase @Inject constructor(
    private val tagRepository: TagRepository,
    private val noteRepository: NoteRepository
) {
    fun addNote(noteUi: NoteUi) = noteRepository.insert(noteUi.toNote())

    fun deleteNote(noteUi: NoteUi) = noteRepository.delete(noteUi.toNote())

    fun updateNote(noteUi: NoteUi) = noteRepository.update(noteUi.toNote())

    fun fetchNotesByTag(tagId: Int, sortedNote: SortedNote) = noteRepository.getByTag(tagId)
        .map { response ->
            response.mapTo { notes ->
                notes.sortNotesBy(sortedNote).map { NoteUi.fromNote(it) }
            }
        }

    fun searchNotes(query: String, searchNoteCategory: SearchNoteCategory) = noteRepository.getAll()
        .map { response ->
            response.mapTo { notes ->
                notes.filterNotesBy(query,searchNoteCategory).map { NoteUi.fromNote(it) }
            }
        }

    fun fetchTagUi(sortBy: SortedTag) = tagRepository.getAll().map { response ->
        response.mapTo { tags ->
            tags.sortTagsBy(sortBy).map { TagUi.fromTag(it) }
        }
    }
}