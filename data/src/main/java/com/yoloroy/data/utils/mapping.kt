package com.yoloroy.data.utils

import com.yoloroy.data.common.Resource
import com.yoloroy.domain.model.NewsDetails
import com.yoloroy.domain.model.NewsShort
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase.DetailsGettingResult
import com.yoloroy.domain.use_case.SearchNewsUseCase.SearchResult

fun Resource<List<NewsShort>>.toSearchResult(): SearchResult = when (this) {
    is Resource.Success -> SearchResult.Success(data)
    is Resource.NoInternetError -> SearchResult.NoInternetConnectionProblem
    else -> SearchResult.UnknownProblem
}

fun Resource<NewsDetails>.toDetailsGettingResult(): DetailsGettingResult = when (this) {
    is Resource.Success -> DetailsGettingResult.Success(data)
    is Resource.NoInternetError -> DetailsGettingResult.NoInternetConnectionProblem
    else -> DetailsGettingResult.UnknownProblem
}
