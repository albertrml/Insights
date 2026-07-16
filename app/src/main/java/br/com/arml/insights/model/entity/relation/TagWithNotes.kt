package br.com.arml.insights.model.entity.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import br.com.arml.insights.model.entity.NoteEntity
import br.com.arml.insights.model.entity.NoteTagLinkEntity
import br.com.arml.insights.model.entity.TagEntity

data class TagWithNotes(
    @Embedded val tag: TagEntity,
    @Relation(
        entity = NoteEntity::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = NoteTagLinkEntity::class,
            parentColumn = "tag_id",
            entityColumn = "note_id"
        )
    )
    val notes: List<NoteEntity>
)
