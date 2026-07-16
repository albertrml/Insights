package br.com.arml.insights.model.entity.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import br.com.arml.insights.model.entity.NoteEntity
import br.com.arml.insights.model.entity.NoteTagLinkEntity
import br.com.arml.insights.model.entity.TagEntity

data class NoteWithTags(
    @Embedded val note: NoteEntity,
    @Relation(
        entity = TagEntity::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = NoteTagLinkEntity::class,
            parentColumn = "note_id",
            entityColumn = "tag_id"
        )
    )
    val tags: List<TagEntity>
)