package com.yoloroy.domain.use_case

import com.yoloroy.domain.model.NewsPredicate
import com.yoloroy.domain.model.NewsShort
import com.yoloroy.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

interface SearchNewsUseCase {

    suspend fun search(predicate: NewsPredicate): Flow<SearchResult>

    class Base(private val repository: NewsRepository) : SearchNewsUseCase {

        override suspend fun search(predicate: NewsPredicate): Flow<SearchResult> = repository.searchNews(predicate)
    }

    sealed class SearchResult {
        class Success(val news: List<NewsShort>) : SearchResult()
        object Loading : SearchResult()
        object NoInternetConnectionProblem : SearchResult()
        object UnknownProblem : SearchResult()
    }
}
