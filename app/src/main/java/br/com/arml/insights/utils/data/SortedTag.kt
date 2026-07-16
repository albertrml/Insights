package br.com.arml.insights.utils.data

import br.com.arml.insights.model.entity.TagEntity
import br.com.arml.insights.utils.data.SortedTag.ByNameAscending
import br.com.arml.insights.utils.data.SortedTag.ByNameDescending

sealed class SortedTag {
    data object ByNameAscending : SortedTag()
    data object ByNameDescending : SortedTag()
}

fun List<TagEntity>.sortTagsBy(sortMethod: SortedTag): List<TagEntity> {
    return when (sortMethod) {
        is ByNameAscending -> this.sortedBy { it.name }
        is ByNameDescending -> this.sortedByDescending { it.name }
    }
}