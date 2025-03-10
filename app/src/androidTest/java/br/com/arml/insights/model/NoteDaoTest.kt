package br.com.arml.insights.model

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.arml.insights.model.source.InsightsRoomDatabase
import br.com.arml.insights.model.source.NoteDao
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before

class NoteDaoTest {

    private lateinit var db: InsightsRoomDatabase
    private lateinit var noteDao: NoteDao
    private val tags = mockTags

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context = context,
            klass = InsightsRoomDatabase::class.java
        ).build()
        noteDao = db.noteDao()
    }

    @Before
    fun clearTables() = runTest { db.clearAllTables() }

    @After
    fun closeDb() = runTest { db.close() }



}