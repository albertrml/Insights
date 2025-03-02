package br.com.arml.insights.model.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.arml.insights.model.entity.Tag
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tag: Tag)

    @Delete
    suspend fun delete(tag: Tag)

    @Update
    suspend fun update(tag: Tag)

    @Query("SELECT * FROM tags_table")
    fun getAll(): Flow<List<Tag>>

    @Query("SELECT EXISTS (SELECT * FROM tags_table WHERE name = :name)")
    suspend fun isTagExists(name: String): Boolean

}