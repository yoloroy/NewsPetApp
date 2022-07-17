package com.yoloroy.room.news

import com.yoloroy.retrofit.news.NewsCache
import com.yoloroy.retrofit.news.model.Article

class NewsCacheImpl(
    private val articleSource: ArticleSource
) : NewsCache {

    override fun cacheArticles(articles: List<Article>): List<Article> =
        articleSource.insert(*articles.toTypedArray())
}
