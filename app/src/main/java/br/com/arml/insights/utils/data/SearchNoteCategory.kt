package br.com.arml.insights.utils.data

import br.com.arml.insights.model.entity.NoteEntity

sealed class SearchNoteCategory {
    data object ByTitle : SearchNoteCategory()
    data object BySituation : SearchNoteCategory()
}

fun List<NoteEntity>.filterNotesBy(query: String, searchNoteCategory: SearchNoteCategory): List<NoteEntity> {
    return when (searchNoteCategory) {
        is SearchNoteCategory.ByTitle ->
            this.filter { it.title.contains(query, ignoreCase = true) }

        is SearchNoteCategory.BySituation ->
            this.filter { it.situation.contains(query, ignoreCase = true) }
    }
}