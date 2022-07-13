package com.yoloroy.data.news

import com.yoloroy.data.common.Resource
import com.yoloroy.domain.model.NewsPredicate
import com.yoloroy.domain.model.NewsShort

interface NewsRemoteSource {

    suspend fun searchNews(predicate: NewsPredicate): Resource<List<NewsShort>>
}
