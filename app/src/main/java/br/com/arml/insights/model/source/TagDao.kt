package br.com.arml.insights.model.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.arml.insights.model.entity.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(tagEntity: TagEntity)

    @Delete
    suspend fun delete(tagEntity: TagEntity)

    @Update
    suspend fun update(tagEntity: TagEntity)

    @Query("SELECT * FROM tags")
    fun getAll(): Flow<List<TagEntity>>

    @Query("SELECT * FROM tags WHERE id = :id")
    suspend fun getById(id: Long): TagEntity?

    @Query("SELECT EXISTS (SELECT 1 FROM tags WHERE name = :name)")
    suspend fun isTagNameExists(name: String): Boolean
}