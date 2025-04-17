package br.com.arml.insights.utils.data

import br.com.arml.insights.model.entity.Note

sealed class SortedNote {
    data object ByTitleAscending : SortedNote()
    data object ByTitleDescending : SortedNote()
    data object ByCreationDateAscending : SortedNote()
    data object ByCreationDateDescending : SortedNote()
    data object BySituationAscending : SortedNote()
    data object BySituationDescending : SortedNote()
}

fun List<Note>.sortNotesBy(sortMethod: SortedNote): List<Note>{
    return when(sortMethod){
        is SortedNote.ByTitleAscending -> this.sortedBy { it.title }
        is SortedNote.ByTitleDescending -> this.sortedByDescending { it.title }
        is SortedNote.ByCreationDateAscending -> this.sortedBy { it.creationDate }
        is SortedNote.ByCreationDateDescending -> this.sortedByDescending { it.creationDate }
        is SortedNote.BySituationAscending -> this.sortedBy { it.situation }
        is SortedNote.BySituationDescending -> this.sortedByDescending { it.situation }
    }
}