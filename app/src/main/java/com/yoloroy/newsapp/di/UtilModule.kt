package com.yoloroy.newsapp.di

import android.content.Context
import com.yoloroy.newsapp.ui.news_list.NewsPredicateUi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    fun provideLocale(@ApplicationContext context: Context): Locale = context.resources.configuration.locales[0]

    @Provides
    fun provideNewsPredicateUiResProducer(@ApplicationContext context: Context): NewsPredicateUi.ResProducer = NewsPredicateUi.ResProducer(context)
}
