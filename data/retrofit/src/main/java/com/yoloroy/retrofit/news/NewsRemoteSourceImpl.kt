package com.yoloroy.retrofit.news

import com.yoloroy.data.common.Resource
import com.yoloroy.data.news.NewsRemoteSource
import com.yoloroy.domain.model.NewsPredicate
import com.yoloroy.domain.model.NewsShort

class NewsRemoteSourceImpl(
    private val api: Nothing // TODO
) : NewsRemoteSource {

    override suspend fun searchNews(predicate: NewsPredicate): Resource<List<NewsShort>> {
        TODO("Not yet implemented")
    }
}
