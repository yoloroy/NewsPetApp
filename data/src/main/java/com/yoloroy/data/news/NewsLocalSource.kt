package com.yoloroy.data.news

import com.yoloroy.data.common.Resource
import com.yoloroy.data.news.model.NewsShortWithId
import com.yoloroy.domain.model.NewsDetails

interface NewsLocalSource {

    suspend fun getDetails(short: NewsShortWithId<*>): Resource<NewsDetails>
}
