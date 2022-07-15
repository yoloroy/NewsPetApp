package com.yoloroy.room.news

import com.yoloroy.data.common.Resource
import com.yoloroy.data.news.NewsLocalSource
import com.yoloroy.data.news.model.NewsShortWithId
import com.yoloroy.domain.model.NewsDetails

class NewsLocalSourceImpl : NewsLocalSource {

    override suspend fun getDetails(short: NewsShortWithId<*>): Resource<NewsDetails> {
        TODO("Not yet implemented")
    }
}
