package com.yoloroy.domain.use_case

import com.yoloroy.domain.model.NewsDetails
import com.yoloroy.domain.model.NewsShort
import com.yoloroy.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

interface GetNewsDetailsUseCase {

    fun getDetails(short: NewsShort): Flow<DetailsGettingResult>

    class Base(private val repository: NewsRepository) : GetNewsDetailsUseCase {

        override fun getDetails(short: NewsShort): Flow<DetailsGettingResult> = repository.getDetails(short)
    }

    sealed class DetailsGettingResult {
        class Success(val details: NewsDetails) : DetailsGettingResult()
        object Loading : DetailsGettingResult()
        object NoInternetConnectionProblem : DetailsGettingResult()
        object UnknownProblem : DetailsGettingResult()
    }
}
