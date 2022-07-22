package com.yoloroy.newsapp.ui.mapper.problem_mapper

import android.content.Context
import com.yoloroy.domain.use_case.SearchNewsUseCase.SearchResult
import com.yoloroy.domain.use_case.SearchNewsUseCase.SearchResult.NoInternetConnectionProblem
import com.yoloroy.domain.use_case.SearchNewsUseCase.SearchResult.UnknownProblem
import com.yoloroy.newsapp.ui.mapper.common.CommonProblemMapper

class ListSearchResultProblemMapper(context: Context) : CommonProblemMapper<SearchResult, UnknownProblem, NoInternetConnectionProblem>(
    context,
    UnknownProblem::class.java,
    NoInternetConnectionProblem::class.java
)
