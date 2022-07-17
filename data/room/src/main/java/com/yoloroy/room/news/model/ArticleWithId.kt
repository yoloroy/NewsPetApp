package com.yoloroy.room.news.model

import com.yoloroy.retrofit.news.model.Article

class ArticleWithId(val id: Long, private val article: Article) : Article by article {

    override fun short() = NewsShortWithId(id, super.short())
    override fun details() = NewsDetailsWithId(id, super.details())
}
