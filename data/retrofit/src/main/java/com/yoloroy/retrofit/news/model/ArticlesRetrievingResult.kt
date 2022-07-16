package com.yoloroy.retrofit.news.model

data class ArticlesRetrievingResult(
    val articles: List<Article.DataClass>,
    val status: String,
    val totalResults: Int
)
