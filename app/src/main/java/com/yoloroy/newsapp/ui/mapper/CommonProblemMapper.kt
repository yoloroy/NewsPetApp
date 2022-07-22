package com.yoloroy.newsapp.ui.mapper

import android.content.Context
import com.yoloroy.newsapp.R

open class CommonProblemMapper<T : Any, UnknownProblem : T, NoInternetConnectionProblem : T>(
    private val context: Context,
    private val unknownProblem: Class<UnknownProblem>,
    private val noInternetConnectionProblem: Class<NoInternetConnectionProblem>
) : ProblemMapper<T> {

    @Suppress("UNCHECKED_CAST")
    override fun map(data: T): String? {
        val resultIs = data::class.java::isAssignableFrom
        return when {
            resultIs(unknownProblem) -> context.getString(R.string.unknown_problem)
            resultIs(noInternetConnectionProblem) -> context.getString(R.string.no_internet_connection)
            else -> null
        }
    }
}
