package com.yoloroy.newsapp.di

import android.content.Context
import com.yoloroy.data.news.NewsInstantSource
import com.yoloroy.data.news.NewsRemoteSource
import com.yoloroy.retrofit.news.NewsApi
import com.yoloroy.retrofit.news.NewsCache
import com.yoloroy.retrofit.news.NewsInstantSourceImpl
import com.yoloroy.retrofit.news.NewsRemoteSourceImpl
import com.yoloroy.retrofit.util.RetrofitProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        @ApplicationContext context: Context
    ): Retrofit = RetrofitProvider.provideRetrofit(context)

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi = RetrofitProvider.provideNewsApi(retrofit)

    @Provides
    @Singleton
    fun provideNewsInstantSource(): NewsInstantSource = NewsInstantSourceImpl()

    @Provides
    @Singleton
    fun provideNewsRemoteSource(api: NewsApi, cache: NewsCache): NewsRemoteSource = NewsRemoteSourceImpl(api, cache)
}
