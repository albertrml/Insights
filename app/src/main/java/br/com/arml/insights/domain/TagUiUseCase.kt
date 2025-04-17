package br.com.arml.insights.domain

import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.model.repository.TagRepository
import br.com.arml.insights.utils.data.SortedTag
import br.com.arml.insights.utils.data.mapTo
import br.com.arml.insights.utils.data.sortTagsBy
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class TagUiUseCase @Inject constructor(private val tagRepository: TagRepository) {

    fun deleteTagUi(tagUi: TagUi) = tagRepository.delete(tagUi.toTag())

    fun fetchTagUi(sortBy: SortedTag) = tagRepository.getAll().map { response ->
        response.mapTo { tags ->
            tags.sortTagsBy(sortBy).map { TagUi.fromTag(it) }
        }
    }

    fun insertTagUi(tagUi: TagUi) = tagRepository.insert(tagUi.toTag())

    suspend fun isTagNameExists(tagName: String) = tagRepository.isTagNameExists(tagName)

    fun updateTagUi(tagUi: TagUi) = tagRepository.update(tagUi.toTag())

    fun searchTagByName(query: String) = tagRepository.getAll().map { response ->
        response.mapTo { tags ->
            tags.filter { it.name.contains(query, ignoreCase = true) }
                .map { TagUi.fromTag(it) }
        }
    }

}