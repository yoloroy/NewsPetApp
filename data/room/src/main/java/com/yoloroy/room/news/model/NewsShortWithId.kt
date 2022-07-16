package com.yoloroy.room.news.model

import com.yoloroy.domain.model.NewsShort

class NewsShortWithId(val id: Int, private val newsShort: NewsShort) : NewsShort by newsShort
