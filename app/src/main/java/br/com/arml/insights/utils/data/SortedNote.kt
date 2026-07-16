package br.com.arml.insights.utils.data

import br.com.arml.insights.model.entity.NoteEntity

sealed class SortedNote {
    data object ByTitleAscending : SortedNote()
    data object ByTitleDescending : SortedNote()
    data object ByCreationDateAscending : SortedNote()
    data object ByCreationDateDescending : SortedNote()
    data object BySituationAscending : SortedNote()
    data object BySituationDescending : SortedNote()
}

fun List<NoteEntity>.sortNotesBy(sortMethod: SortedNote): List<NoteEntity>{
    return when(sortMethod){
        is SortedNote.ByTitleAscending -> this.sortedBy { it.title }
        is SortedNote.ByTitleDescending -> this.sortedByDescending { it.title }
        is SortedNote.ByCreationDateAscending -> this.sortedBy { it.creationDate }
        is SortedNote.ByCreationDateDescending -> this.sortedByDescending { it.creationDate }
        is SortedNote.BySituationAscending -> this.sortedBy { it.situation }
        is SortedNote.BySituationDescending -> this.sortedByDescending { it.situation }
    }
}