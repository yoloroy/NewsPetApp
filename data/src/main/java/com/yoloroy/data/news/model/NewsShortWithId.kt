package com.yoloroy.data.news.model

import com.yoloroy.domain.model.NewsShort

class NewsShortWithId<T>(val id: T, private val newsShort: NewsShort) : NewsShort by newsShort
