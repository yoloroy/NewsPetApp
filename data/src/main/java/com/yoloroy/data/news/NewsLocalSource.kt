package com.yoloroy.data.news

import com.yoloroy.data.common.Resource
import com.yoloroy.domain.model.NewsDetails
import com.yoloroy.domain.model.NewsShort

interface NewsLocalSource {

    suspend fun getDetails(short: NewsShort): Resource<NewsDetails>
}
