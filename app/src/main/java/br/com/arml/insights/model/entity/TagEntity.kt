package br.com.arml.insights.model.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tags",
    indices = [
        Index(
            value = ["name"],
            name = "index_tags_name"
        )
    ]
)
data class TagEntity(
    @PrimaryKey (autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val color: Long,
    val description: String
){
    override fun equals(other: Any?): Boolean {
        val other = other as TagEntity
        return id == other.id && name == other.name && description == other.description
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + color.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        return result.toInt()
    }
}