package com.yoloroy.data.common

sealed class Resource<T> {

    class Success<T>(val data: T) : Resource<T>()

    sealed class Error<T> : Resource<T>()

    class NoInternetError<T> : Error<T>()
    class UnknownError<T> : Error<T>()

    fun <R> map(mapper: (T) -> R) = when(this) {
        is Success -> Success(mapper(data))
        is NoInternetError -> NoInternetError()
        is UnknownError -> UnknownError()
    }
}
