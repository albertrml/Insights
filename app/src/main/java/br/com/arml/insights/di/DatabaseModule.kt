package br.com.arml.insights.di

import android.content.Context
import androidx.room.Room
import br.com.arml.insights.model.source.InsightsRoomDatabase
import br.com.arml.insights.model.source.migration.MIGRATION_2_3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DATABASE_NAME = "insights_database"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): InsightsRoomDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            InsightsRoomDatabase::class.java,
            DATABASE_NAME
        )
        .addMigrations(MIGRATION_2_3)
        .build()

    @Provides
    @Singleton
    fun provideTagDao(database: InsightsRoomDatabase) = database.tagDao()

    @Provides
    @Singleton
    fun provideNoteDao(database: InsightsRoomDatabase) = database.noteDao()
}