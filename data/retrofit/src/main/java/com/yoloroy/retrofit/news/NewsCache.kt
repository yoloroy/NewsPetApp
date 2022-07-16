package com.yoloroy.retrofit.news

import com.yoloroy.retrofit.news.model.Article

interface NewsCache {

    fun cacheArticles(article: List<Article>): List<Article>
}
