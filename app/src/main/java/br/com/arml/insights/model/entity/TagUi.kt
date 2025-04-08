package br.com.arml.insights.model.entity

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

data class TagUi(
    val id: Int,
    val name: String,
    val color: Color,
    val description: String,
){
    /*@OptIn(ExperimentalStdlibApi::class)
    fun toTag() = Tag(
        id = id,
        name = name,
        color = color.toArgb().toLong(),
        description = description
    )*/

    companion object{
        fun fromTag(tag: Tag?) = tag?.let {
            TagUi(
                id = tag.id,
                name = tag.name,
                color = Color(tag.color),
                description = tag.description,
            )
        } ?: TagUi(
            id = 0,
            name = "",
            color = Color.Black,
            description = "",
        )
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
            id = list[0] as Int,
            name = list[1] as String,
            color = Color(list[2] as Long),
            description = list[3] as String
        )
    }
)