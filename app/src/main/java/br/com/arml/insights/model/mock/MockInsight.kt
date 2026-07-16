package br.com.arml.insights.model.mock

import br.com.arml.insights.model.entity.NoteEntity
import br.com.arml.insights.model.entity.TagEntity
import java.util.Date

val mockTagEntities = arrayListOf(
    TagEntity(
        id = 0,
        name = "Dinner",
        color = -15108398L, //0xFF1976D2,
        description = "Ideas for dinner recipes"
    ),
    TagEntity(
        id = 1,
        name = "App",
        color = -442044L, //0xFFF94144,
        description = "Ideas for project app"
    ),
    TagEntity(
        id = 2,
        name = "Beer",
        color = -15825407L, //0xFF0E8601,
        description = "Ideas for beer recipes"
    ),
    TagEntity(
        id = 3,
        name = "Business",
        color = -28928L, //0xFFFF8F00,
        description = "Business Ideas"
    ),
    TagEntity(
        id = 4,
        name = "Dinheiro",
        color = -442044L, //0xFFF94144,
        description = "Ideas for Money"
    ),
)

fun MutableList<NoteEntity>.addMockNotes(tagEntity: TagEntity, quantity: Int) {
    val currentDate = Date().time
    (1..quantity).forEach { i ->
        val noteEntity = NoteEntity(
            title = "${tagEntity.name} $i Title",
            body = "${tagEntity.name} $i Body",
            situation = "${tagEntity.name} $i Situation",
            creationDate = currentDate + i,
        )
        this.add(noteEntity)
    }
}