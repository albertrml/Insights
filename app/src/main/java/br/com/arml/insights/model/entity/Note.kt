package br.com.arml.insights.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes_table",
    foreignKeys = [
        ForeignKey(
            entity = Tag::class,
            parentColumns = ["name"],
            childColumns = ["tag"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["tag"])]
)
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val body: String,
    val situation: String,
    @ColumnInfo(name = "creation_date")
    val creationDate: Long,
    val tag: String
)