package br.com.arml.insights.model.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import br.com.arml.insights.model.entity.NoteEntity
import br.com.arml.insights.model.entity.relation.NoteWithTags
import br.com.arml.insights.model.entity.relation.TagWithNotes
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    /** CREATE **/
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(noteEntity: NoteEntity)

    /** READ **/
    @Query("SELECT * FROM notes")
    fun getAll(): Flow<List<NoteEntity>>

    @Query(
        """
        SELECT notes.* FROM notes 
        INNER JOIN note_tag_links ON notes.id = note_tag_links.note_id 
        WHERE note_tag_links.tag_id = :tagId
    """
    )
    fun getByTag(tagId: Long): Flow<List<NoteEntity>>

    @Transaction
    @Query("SELECT * FROM tags WHERE id = :tagId")
    fun getTagWithNotes(tagId: Long): Flow<List<TagWithNotes>>

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteWithTags(noteId: Long): Flow<List<NoteWithTags>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getById(noteId: Long): NoteEntity?

    /** UPDATE **/
    @Update
    suspend fun update(noteEntity: NoteEntity)

    /** DELETE **/
    @Delete
    suspend fun delete(noteEntity: NoteEntity)
}