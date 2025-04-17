package br.com.arml.insights.model.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.arml.insights.model.entity.Note
import br.com.arml.insights.model.entity.Tag

@Database(entities = [ Note::class, Tag::class ], version = 2, exportSchema = false)
abstract class InsightsRoomDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun tagDao(): TagDao

    companion object{
        @Volatile
        private var INSTANCE: InsightsRoomDatabase? = null

        fun getDatabase(context: Context): InsightsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InsightsRoomDatabase::class.java,
                    "insights_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}