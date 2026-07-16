package br.com.arml.insights.domain

import br.com.arml.core.response.mapSuccess
import br.com.arml.insights.model.domain.TagUi
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.utils.data.SortedTag
import br.com.arml.insights.utils.data.sortTagsBy
import javax.inject.Inject


class TagUiUseCase @Inject constructor(private val tagRepository: TagRepository) {
    /** CREATE **/
    fun insertTagUi(tagUi: TagUi) = tagRepository.insert(tagUi.toTagEntity())

    /** READ **/
    suspend fun isTagNameExists(tagName: String): Boolean {
        /*tagRepository.getTagById(index)?.let {
            if (it.name == tagName) return false
        }*/
        return tagRepository.isTagNameExists(tagName)
    }

    fun fetchTagUi(sortBy: SortedTag) = tagRepository
        .getAll()
        .mapSuccess { tags ->
            tags.sortTagsBy(sortBy).map { TagUi.fromTagEntity(it) }
        }

    fun searchTagByName(query: String) = tagRepository
        .getAll()
        .mapSuccess { tags ->
            tags
                .filter { it.name.contains(query, ignoreCase = true) }
                .map { TagUi.fromTagEntity(it) }
        }

    /** UPDATE **/
    fun updateTagUi(tagUi: TagUi) = tagRepository.update(tagUi.toTagEntity())

    /** DELETE **/
    fun deleteTagUi(tagUi: TagUi) = tagRepository.delete(tagUi.toTagEntity())


}