package br.com.arml.insights.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "note_tag_links",
    primaryKeys = ["note_id", "tag_id"],
    foreignKeys = [
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = ["id"],
            childColumns = ["note_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["id"],
            childColumns = ["tag_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.NO_ACTION
        )
    ],
    indices = [
        Index(
            value = ["note_id"],
            name = "index_note_tag_links_note_id"
        ),
        Index(
            value = ["tag_id"],
            name = "index_note_tag_links_tag_id"
        )
    ]
)
data class NoteTagLinkEntity(
    @ColumnInfo(name = "note_id")
    val noteId: Long,

    @ColumnInfo(name = "tag_id")
    val tagId: Long
)
