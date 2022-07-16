package com.yoloroy.room.news.model

import com.yoloroy.domain.model.NewsDetails

class NewsDetailsWithId(val id: Int, private val newsDetails: NewsDetails) : NewsDetails by newsDetails
