package com.yoloroy.retrofit.news.model

import com.yoloroy.domain.model.NewsFilterData

fun Article.toNewsFilterData() = NewsFilterData(title, description, content)
