package br.com.arml.insights.domain

import br.com.arml.core.response.mapSuccess
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.utils.data.SortedTag
import br.com.arml.insights.utils.data.sortTagsBy
import javax.inject.Inject


class TagUiUseCase @Inject constructor(private val tagRepository: TagRepository) {

    fun deleteTagUi(tagUi: TagUi) = tagRepository.delete(tagUi.toTag())

    fun fetchTagUi(sortBy: SortedTag) = tagRepository.getAll()
        .mapSuccess { tags ->
            tags.sortTagsBy(sortBy).map { TagUi.fromTag(it) }
        }

    fun insertTagUi(tagUi: TagUi) = tagRepository.insert(tagUi.toTag())

    suspend fun isTagNameExists(
        index: Int,
        tagName: String
    ): Boolean {
        tagRepository.getTagById(index)?.let {
            if (it.name == tagName) return false
        }
        return tagRepository.isTagNameExists(tagName)
    }

    fun updateTagUi(tagUi: TagUi) = tagRepository.update(tagUi.toTag())

    fun searchTagByName(query: String) = tagRepository.getAll()
        .mapSuccess { tags ->
            tags.filter { it.name.contains(query, ignoreCase = true) }
                .map { TagUi.fromTag(it) }

        }
}