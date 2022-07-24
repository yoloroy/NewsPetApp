package com.yoloroy.newsapp.di

import android.content.Context
import com.yoloroy.data.news.NewsLocalSource
import com.yoloroy.retrofit.news.NewsCache
import com.yoloroy.room.db.AppDatabase
import com.yoloroy.room.db.DbProvider
import com.yoloroy.room.db.dao.ArticleDao
import com.yoloroy.room.news.ArticleSource
import com.yoloroy.room.news.NewsCacheImpl
import com.yoloroy.room.news.NewsLocalSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = DbProvider.provide(context)

    @Provides
    @Singleton
    fun provideNewsLocalSource(articleSource: ArticleSource): NewsLocalSource = NewsLocalSourceImpl(articleSource)

    @Provides
    @Singleton
    fun provideNewsCache(articleSource: ArticleSource): NewsCache = NewsCacheImpl(articleSource)

    @Provides
    @Singleton
    fun provideArticleSource(articleDao: ArticleDao): ArticleSource = ArticleSource.Base(articleDao)

    @Provides
    @Singleton
    fun provideArticleDao(db: AppDatabase): ArticleDao = db.articleDao()
}
