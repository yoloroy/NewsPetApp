package com.yoloroy.retrofit.news

import com.yoloroy.data.news.NewsInstantSource
import com.yoloroy.domain.model.NewsDetails
import com.yoloroy.domain.model.NewsShort
import com.yoloroy.retrofit.news.model.Article

class NewsInstantSourceImpl : NewsInstantSource {

    override fun getDetails(short: NewsShort): NewsDetails? = (short as? Article.Short)?.details()
}
