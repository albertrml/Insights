package br.com.arml.insights.model.mock

import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.entity.Tag
import java.util.Date

val mockTags = arrayListOf(
    Tag(
        id = 0,
        name = "Dinner",
        color = -15108398L, //0xFF1976D2,
        description = "Ideas for dinner recipes"
    ),
    Tag(
        id = 1,
        name = "App",
        color = -442044L, //0xFFF94144,
        description = "Ideas for project app"
    ),
    Tag(
        id = 2,
        name = "Beer",
        color = -15825407L, //0xFF0E8601,
        description = "Ideas for beer recipes"
    ),
    Tag(
        id = 3,
        name = "Business",
        color = -28928L, //0xFFFF8F00,
        description = "Business Ideas"
    ),
    Tag(
        id = 4,
        name = "Dinheiro",
        color = -442044L, //0xFFF94144,
        description = "Ideas for Money"
    ),
)

fun MutableList<Note>.addMockNotes(tag: Tag, quantity: Int) {
    val currentDate = Date().time
    (1..quantity).map { i ->
        val note = Note(
            title = "${tag.name} $i Title",
            body = "${tag.name} $i Body",
            situation = "${tag.name} $i Situation",
            creationDate = currentDate + i,
            tagId = tag.id
        )
        this.add(note)
    }
}