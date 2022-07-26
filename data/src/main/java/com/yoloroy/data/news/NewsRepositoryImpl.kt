package com.yoloroy.data.news

import com.yoloroy.data.utils.toDetailsGettingResult
import com.yoloroy.data.utils.toSearchResult
import com.yoloroy.domain.model.NewsPredicate
import com.yoloroy.domain.model.NewsShort
import com.yoloroy.domain.repository.NewsRepository
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase.DetailsGettingResult
import com.yoloroy.domain.use_case.SearchNewsUseCase.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(
    private val remoteSource: NewsRemoteSource,
    private val localSource: NewsLocalSource,
    private val instantSource: NewsInstantSource
) : NewsRepository {

    override fun searchNews(predicate: NewsPredicate): Flow<SearchResult> = flow {
        emit(SearchResult.Loading)
        emit(remoteSource.searchNews(predicate).toSearchResult())
    }

    override fun getDetails(short: NewsShort): Flow<DetailsGettingResult> = flow {
        emit(
            instantSource.getDetails(short)
                ?.let { DetailsGettingResult.Success(it) }
                ?: DetailsGettingResult.Loading
        )

        emit(localSource.getDetails(short).toDetailsGettingResult())
    }
}
