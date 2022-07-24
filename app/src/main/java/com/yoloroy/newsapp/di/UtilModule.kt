package com.yoloroy.newsapp.di

import android.content.Context
import android.os.Build
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
    fun provideLocale(@ApplicationContext context: Context): Locale = context.resources.configuration.let { configuration ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.locales[0]
        } else {
            configuration.locale
        }
    }

    @Provides
    fun provideNewsPredicateUiResProducer(@ApplicationContext context: Context): NewsPredicateUi.ResProducer = NewsPredicateUi.ResProducer(context)
}
