package com.yoloroy.newsapp.di

import com.yoloroy.data.news.NewsInstantSource
import com.yoloroy.data.news.NewsLocalSource
import com.yoloroy.data.news.NewsRemoteSource
import com.yoloroy.data.news.NewsRepositoryImpl
import com.yoloroy.domain.repository.NewsRepository
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase
import com.yoloroy.domain.use_case.SearchNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        remoteSource: NewsRemoteSource,
        localSource: NewsLocalSource,
        instantSource: NewsInstantSource
    ): NewsRepository = NewsRepositoryImpl(remoteSource, localSource, instantSource)

    @Provides
    fun provideSearchNewsUseCase(
        repository: NewsRepository
    ): SearchNewsUseCase = SearchNewsUseCase.Base(repository)

    @Provides
    fun provideGetNewsDetailsUseCase(
        repository: NewsRepository
    ): GetNewsDetailsUseCase = GetNewsDetailsUseCase.Base(repository)
}
