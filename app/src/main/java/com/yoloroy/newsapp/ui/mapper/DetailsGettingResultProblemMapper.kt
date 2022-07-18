package com.yoloroy.newsapp.ui.mapper

import android.content.Context
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase.DetailsGettingResult
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase.DetailsGettingResult.NoInternetConnectionProblem
import com.yoloroy.domain.use_case.GetNewsDetailsUseCase.DetailsGettingResult.UnknownProblem
import com.yoloroy.newsapp.R

class DetailsGettingResultProblemMapper(
    private val context: Context
) : Mapper<DetailsGettingResult, String?> {

    override fun map(data: DetailsGettingResult) = when (data) {
        is UnknownProblem -> context.getString(R.string.unknown_problem)
        is NoInternetConnectionProblem -> context.getString(R.string.no_internet_connection)
        else -> null
    }
}
