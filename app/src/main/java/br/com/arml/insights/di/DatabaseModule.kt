package br.com.arml.insights.di

import android.content.Context
import br.com.arml.insights.model.source.InsightsRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): InsightsRoomDatabase {
        return InsightsRoomDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideTagDao(database: InsightsRoomDatabase) = database.tagDao()

    @Provides
    @Singleton
    fun provideNoteDao(database: InsightsRoomDatabase) = database.noteDao()

}