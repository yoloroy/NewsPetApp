package com.yoloroy.room.news.model

import com.yoloroy.domain.model.NewsShort

class NewsShortWithId(val id: Long, private val newsShort: NewsShort) : NewsShort by newsShort
