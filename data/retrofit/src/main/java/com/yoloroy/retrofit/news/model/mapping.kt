package com.yoloroy.retrofit.news.model

fun ArticlesRetrievingResult.toNewsShortList() = articles.map(Article::Short)
