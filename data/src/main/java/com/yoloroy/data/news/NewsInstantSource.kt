package com.yoloroy.data.news

import com.yoloroy.domain.model.NewsDetails
import com.yoloroy.domain.model.NewsShort

interface NewsInstantSource {

    fun getDetails(short: NewsShort): NewsDetails?
}
