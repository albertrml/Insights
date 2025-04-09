package br.com.arml.insights.di

import br.com.arml.insights.domain.TagUiUseCase
import br.com.arml.insights.model.repository.TagRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideTagUiUseCase(tagRepository: TagRepository): TagUiUseCase {
        return TagUiUseCase(tagRepository)
    }

}