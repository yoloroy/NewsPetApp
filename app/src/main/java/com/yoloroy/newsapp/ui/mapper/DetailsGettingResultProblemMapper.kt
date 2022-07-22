package com.yoloroy.newsapp.ui.mapper

import android.content.Context
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase.DetailsGettingResult
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase.DetailsGettingResult.NoInternetConnectionProblem
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase.DetailsGettingResult.UnknownProblem

class DetailsGettingResultProblemMapper(context: Context) : CommonProblemMapper<DetailsGettingResult, UnknownProblem, NoInternetConnectionProblem>(
    context,
    UnknownProblem::class.java,
    NoInternetConnectionProblem::class.java
)
