package br.com.arml.insights.model.mock

import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.entity.Tag
import java.util.Date

val mockTags = arrayListOf(
    Tag(
        id = 0,
        name = "Dinner",
        color = "#111111",
        description = "Ideas for dinner recipes"
    ),
    Tag(
        id = 0,
        name = "App",
        color = "#333333",
        description = "Ideas for project app"
    ),
    Tag(
        id = 0,
        name = "Beer",
        color = "#222222",
        description = "Ideas for beer recipes"
    ),
    Tag(
        id = 0,
        name = "Business",
        color = "#000000",
        description = "Business Ideas"
    )
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