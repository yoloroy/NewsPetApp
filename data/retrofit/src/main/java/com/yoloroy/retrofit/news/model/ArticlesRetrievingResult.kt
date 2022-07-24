package com.yoloroy.retrofit.news.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticlesRetrievingResult(
    val articles: List<Article.DataClass>,
    val status: String,
    val totalResults: Int
)
