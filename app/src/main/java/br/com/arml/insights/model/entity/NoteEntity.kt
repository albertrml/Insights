package br.com.arml.insights.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes",
    indices = [
        Index(
            value = ["id"],
            name = "index_notes_id"
        ),
        Index(
            value = ["creation_date"],
            name = "index_notes_creation_date"
        )
    ]
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val body: String,
    val situation: String,
    @ColumnInfo(name = "creation_date")
    val creationDate: Long
)