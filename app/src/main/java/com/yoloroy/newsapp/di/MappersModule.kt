package com.yoloroy.newsapp.di

import android.content.Context
import com.yoloroy.domain.model.NewsDetails
import com.yoloroy.domain.model.NewsShort
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase
import com.yoloroy.domain.use_case.SearchNewsUseCase
import com.yoloroy.newsapp.ui.mapper.Mapper
import com.yoloroy.newsapp.ui.mapper.common.CalendarToStringMapper
import com.yoloroy.newsapp.ui.mapper.model_mapper.NewsDetailsToUiMapper
import com.yoloroy.newsapp.ui.mapper.model_mapper.NewsShortToUiMapper
import com.yoloroy.newsapp.ui.mapper.problem_mapper.DetailsGettingResultProblemMapper
import com.yoloroy.newsapp.ui.mapper.problem_mapper.ListSearchResultProblemMapper
import com.yoloroy.newsapp.ui.model.NewsDetailsUi
import com.yoloroy.newsapp.ui.model.NewsShortUi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MappersModule {

    @Provides
    @Singleton
    fun provideDetailsGettingResultProblemMapper(
        @ApplicationContext context: Context
    ): Mapper<GetNewsDetailsUseCase.DetailsGettingResult, String?> = DetailsGettingResultProblemMapper(context)

    @Provides
    @Singleton
    fun provideListSearchResultProblemMapper(
        @ApplicationContext context: Context
    ): Mapper<SearchNewsUseCase.SearchResult, String?> = ListSearchResultProblemMapper(context)

    @Provides
    @Singleton
    fun provideNewsDetailsToUiMapper(
        dateMapper: Mapper<Calendar, String>
    ): Mapper<NewsDetails, NewsDetailsUi> = NewsDetailsToUiMapper(dateMapper)

    @Provides
    @Singleton
    fun provideNewsShortToUiMapper(
        dateMapper: Mapper<Calendar, String>
    ): Mapper<NewsShort, NewsShortUi> = NewsShortToUiMapper(dateMapper)

    @Provides
    @Singleton
    fun provideCalendarToStringMapper(
        locale: Locale
    ): Mapper<Calendar, String> = CalendarToStringMapper(locale)
}
