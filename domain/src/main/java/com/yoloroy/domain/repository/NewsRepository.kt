package com.yoloroy.domain.repository

import com.yoloroy.domain.model.NewsPredicate
import com.yoloroy.domain.model.NewsShort
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase
import com.yoloroy.domain.use_case.SearchNewsUseCase
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun searchNews(predicate: NewsPredicate): Flow<SearchNewsUseCase.SearchResult>

    fun getDetails(short: NewsShort): Flow<GetNewsDetailsUseCase.DetailsGettingResult>
}
