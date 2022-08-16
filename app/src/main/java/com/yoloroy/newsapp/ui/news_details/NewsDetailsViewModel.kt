package com.yoloroy.newsapp.ui.news_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoloroy.domain.model.NewsDetails
import com.yoloroy.domain.model.NewsShort
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase.DetailsGettingResult
import com.yoloroy.newsapp.ui.mapper.Mapper
import com.yoloroy.newsapp.ui.model.NewsDetailsUi
import com.yoloroy.newsapp.ui.model.NewsShortUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val getNewsDetailsUseCase: GetNewsDetailsUseCase,
    private val newsDetailsMapper: Mapper<NewsDetails, NewsDetailsUi>,
    private val detailsGettingProblemMapper: Mapper<DetailsGettingResult, String?>,
    private val newsShortUiToModelMapper: Mapper<NewsShortUi, NewsShort>
) : ViewModel() {

    private val detailsGettingResultFlow = MutableStateFlow<DetailsGettingResult>(DetailsGettingResult.Loading)

    val newsDetailsUi: Flow<NewsDetailsUi> get() = detailsGettingResultFlow
        .filterIsInstance<DetailsGettingResult.Success>()
        .map { newsDetailsMapper.map(it.details).also { Log.i(tag, "$it") } }

    val isLoading: Flow<Boolean> get() = detailsGettingResultFlow
        .map { it is DetailsGettingResult.Loading }

    val problem: Flow<String?> get() = detailsGettingResultFlow
        .map { detailsGettingProblemMapper.map(it) }

    fun init(shortUi: NewsShortUi) {
        val short = newsShortUiToModelMapper.map(shortUi.also { Log.i(tag, "$it") }).also { Log.i(tag, "$it") }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getNewsDetailsUseCase.getDetails(short).collect {
                    detailsGettingResultFlow.emit(it)
                }
            }
        }
    }

    companion object {
        private const val tag = "NewsDVM"
    }
}
