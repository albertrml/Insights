package br.com.arml.insights.model.domain

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import br.com.arml.insights.model.entity.TagEntity
import br.com.arml.insights.utils.exception.TagException

const val MIN_TAG_NAME_LENGTH = 3
const val MAX_TAG_NAME_LENGTH = 20

data class TagUi(
    val id: Long,
    val name: String,
    val color: Color,
    val description: String,
){
    @OptIn(ExperimentalStdlibApi::class)
    fun toTagEntity() = TagEntity(
        id = id,
        name = name,
        color = color.toArgb().toLong(),
        description = description
    )

    companion object{
        fun fromTagEntity(tagEntity: TagEntity?) = tagEntity?.let {
            TagUi(
                id = tagEntity.id,
                name = tagEntity.name,
                color = Color(tagEntity.color),
                description = tagEntity.description,
            )
        } ?: TagUi(
            id = 0,
            name = "",
            color = Color.Black,
            description = "",
        )

        fun isValid(tagUi: TagUi?): Unit = when {
            tagUi == null ->
                throw TagException.TagIsNullException()

            tagUi.name.length !in MIN_TAG_NAME_LENGTH..MAX_TAG_NAME_LENGTH ->
                throw TagException.TagNameSizeException()

            else -> Unit
        }

    }
}

val TagUiSaver: Saver<TagUi, *> = listSaver(
    save = { tagUi ->
        listOf(
            tagUi.id,
            tagUi.name,
            tagUi.color.toArgb().toLong(),
            tagUi.description
        )
    },
    restore = { list ->
        TagUi(
            id = list[0] as Long,
            name = list[1] as String,
            color = Color(list[2] as Long),
            description = list[3] as String
        )
    }
)