package com.yoloroy.newsapp.ui.news_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoloroy.domain.model.NewsPredicate
import com.yoloroy.domain.model.NewsShort
import com.yoloroy.domain.model.sum
import com.yoloroy.domain.use_case.SearchNewsUseCase
import com.yoloroy.domain.use_case.SearchNewsUseCase.SearchResult
import com.yoloroy.newsapp.ui.mapper.Mapper
import com.yoloroy.newsapp.ui.model.NewsShortUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.yoloroy.newsapp.ui.news_list.NewsPredicateUi.ResProducer.Type as ResType

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase,
    private val newsSearchMapper: Mapper<NewsShort, NewsShortUi>,
    private val searchProblemMapper: Mapper<SearchResult, String?>,
    newsPredicateResProducer: NewsPredicateUi.ResProducer
) : ViewModel() {

    private val newsSearchResultFlow = MutableStateFlow<SearchResult>(SearchResult.Loading)

    val news: Flow<List<NewsShortUi>> get() = newsSearchResultFlow
        .filterIsInstance<SearchResult.Success>()
        .map { it.news.map(newsSearchMapper::map) }
        .onStart { emit(emptyList()) }

    private val _predicates = MutableStateFlow(emptyList<NewsPredicateUi>())
    val predicates: Flow<List<NewsPredicateUi>> get() = _predicates

    val availablePredicates get() = predicates.map { initialPredicates - it.toSet() }

    val isLoading: Flow<Boolean> get() = newsSearchResultFlow.map { it is SearchResult.Loading }
    val problem: Flow<String?> get() = newsSearchResultFlow.map(searchProblemMapper::map)

    fun addPredicate(predicate: NewsPredicateUi) = _predicates.update { it + predicate }
    fun updatePredicate(index: Int, newFieldData: String) = _predicates.update { predicates ->
        predicates.apply {
            get(index).changeData(newFieldData)
        }
    }

    init {
        viewModelScope.launch {
            Log.i(tag, "__init__")
            withContext(Dispatchers.IO) {
                updateSearchResults(predicates.first().toSearchPredicate())
                predicates.collectLatest { predicates ->
                    updateSearchResults(predicates.toSearchPredicate())
                }
            }
        }
    }

    private suspend fun updateSearchResults(searchPredicate: NewsPredicate) {
        Log.i(tag, "updateSearchResults")
        newsSearchResultFlow.emit(SearchResult.Loading)
        searchNewsUseCase.search(searchPredicate)
            .collectLatest { newsSearchResultFlow.emit(it) }
    }

    private fun List<NewsPredicateUi>.toSearchPredicate() = map { it.toPredicate() }.sum()

    private val initialPredicates = with(newsPredicateResProducer) { // TODO move to constructor
        listOf(
            produce(ResType.TitleContains),
            produce(ResType.DescriptionContains),
            produce(ResType.ContentContains))
    }

    companion object {
        const val tag = "NewsListVM"
    }
}
