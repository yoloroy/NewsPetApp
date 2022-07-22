package com.yoloroy.newsapp.ui.mapper.problem_mapper

import android.content.Context
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase.DetailsGettingResult
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase.DetailsGettingResult.NoInternetConnectionProblem
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase.DetailsGettingResult.UnknownProblem
import com.yoloroy.newsapp.ui.mapper.common.CommonProblemMapper

class DetailsGettingResultProblemMapper(context: Context) : CommonProblemMapper<DetailsGettingResult, UnknownProblem, NoInternetConnectionProblem>(
    context,
    UnknownProblem::class.java,
    NoInternetConnectionProblem::class.java
)
