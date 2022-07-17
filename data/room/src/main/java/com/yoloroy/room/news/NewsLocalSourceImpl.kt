package com.yoloroy.room.news

import android.util.Log
import com.yoloroy.data.common.Resource
import com.yoloroy.data.news.NewsLocalSource
import com.yoloroy.domain.model.NewsDetails
import com.yoloroy.domain.model.NewsShort
import com.yoloroy.room.news.model.NewsShortWithId

class NewsLocalSourceImpl(private val articleSource: ArticleSource) : NewsLocalSource {

    override suspend fun getDetails(short: NewsShort): Resource<NewsDetails> {
        return if (short is NewsShortWithId) {
            Resource.Success(articleSource.getById(short.id).details())
        } else {
            Log.e("NewsPeroImpl", "this article hasn't been cached")
            Resource.UnknownError()
        }
    }
}
