package br.com.arml.insights.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags_table")
data class Tag(
    @PrimaryKey
    val name: String,
    val color: String = "#d4d4d4",
    val description: String
)