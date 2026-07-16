package br.com.arml.insights.model.source

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.arml.insights.model.entity.NoteEntity
import br.com.arml.insights.model.entity.NoteTagLinkEntity
import br.com.arml.insights.model.entity.TagEntity

@Database(
    entities = [
        NoteEntity::class,
        TagEntity::class,
        NoteTagLinkEntity::class
    ],
    version = 3,
    exportSchema = true
)
abstract class InsightsRoomDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun tagDao(): TagDao
}