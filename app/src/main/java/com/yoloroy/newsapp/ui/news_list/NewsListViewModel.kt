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
import com.yoloroy.newsapp.ui.news_list.NewsPredicateUi.PredicateStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

    private val _predicates: MutableStateFlow<List<NewsPredicateUi>>
    private var predicatesChanged = false
    val predicates: StateFlow<List<NewsPredicateUi>> get() = _predicates

    val isLoading: Flow<Boolean> get() = newsSearchResultFlow.map { it is SearchResult.Loading }
    val problem: Flow<String?> get() = newsSearchResultFlow.map(searchProblemMapper::map)

    fun applyPredicate(predicate: NewsPredicateUi, newData: String) = updatePredicate(
        predicate.copy(status = PredicateStatus.Applied, fieldData = newData)
    )
    fun removePredicate(predicate: NewsPredicateUi) = updatePredicate(
        predicate.copy(status = PredicateStatus.Available)
    )
    fun updatePredicate(predicate: NewsPredicateUi) = _predicates.update { predicates ->
        predicatesChanged = true
        predicates.map {
            if (it.type == predicate.type) predicate else it
        }
    }

    private var firstInit = true
    fun init() {
        Log.i(tag, "__init__")
        if (firstInit) {
            viewModelScope.launch(Dispatchers.IO) {
                updateSearchResults(predicates.value.toSearchPredicate())
                firstInit = false
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            predicates.collectLatest { predicates ->
                if (!predicatesChanged) return@collectLatest
                updateSearchResults(predicates.toSearchPredicate())
            }
        }
    }

    private suspend fun updateSearchResults(searchPredicate: NewsPredicate) {
        predicatesChanged = false
        Log.i(tag, "updateSearchResults")
        newsSearchResultFlow.emit(SearchResult.Loading)
        searchNewsUseCase.search(searchPredicate)
            .collectLatest { newsSearchResultFlow.emit(it) }
    }

    private fun List<NewsPredicateUi>.toSearchPredicate() = this
        .filter { it.status == PredicateStatus.Applied }
        .map { it.toPredicate() }
        .sum()

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
        _predicates = MutableStateFlow(initialPredicates)
    }

    companion object {
        const val tag = "NewsListVM"
    }
}
