package br.com.arml.insights.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags_table")
data class Tag(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val color: Long,
    val description: String
){
    override fun equals(other: Any?): Boolean {
        val other = other as Tag
        return id == other.id && name == other.name && description == other.description
    }
}