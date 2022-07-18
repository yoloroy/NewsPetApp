package com.yoloroy.newsapp.ui.mapper

interface Mapper<T, R> {
    fun map(data: T): R
}
