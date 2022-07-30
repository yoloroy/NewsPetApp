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

    fun togglePredicate(predicate: NewsPredicateUi) = _predicates.update {
        if (it.contains(predicate)) it - predicate else it + predicate
    }
    fun addPredicate(predicate: NewsPredicateUi) = _predicates.update { it + predicate }
    fun removePredicate(predicate: NewsPredicateUi) = _predicates.update { it - predicate }
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

    private val initialPredicates: List<NewsPredicateUi>

    val titleContainsPredicateUi: NewsPredicateUi
    val descriptionContainsPredicateUi: NewsPredicateUi
    val contentContainsPredicateUi: NewsPredicateUi

    init {
        with(newsPredicateResProducer) {
            titleContainsPredicateUi = produce(ResType.TitleContains)
            descriptionContainsPredicateUi = produce(ResType.DescriptionContains)
            contentContainsPredicateUi = produce(ResType.ContentContains)
        }
        initialPredicates = listOf(
            titleContainsPredicateUi,
            descriptionContainsPredicateUi,
            contentContainsPredicateUi
        )
    }

    companion object {
        const val tag = "NewsListVM"
    }
}
