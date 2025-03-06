package br.com.arml.insights.model.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.arml.insights.model.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM notes_table")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM notes_table WHERE tag_id = :tagId")
    fun getByTag(tagId: Int): Flow<List<Note>>

}