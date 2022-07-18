package com.yoloroy.newsapp.ui.mapper

import com.yoloroy.domain.model.NewsDetails
import com.yoloroy.newsapp.ui.model.NewsDetailsUi
import java.util.*

class NewsDetailsToUiMapper(locale: Locale) : Mapper<NewsDetails, NewsDetailsUi> {
    private val dateMapper = CalendarToStringMapper(locale)

    override fun map(data: NewsDetails) = NewsDetailsUi(
        data.title,
        data.imageUrl,
        data.content ?: "",
        dateMapper.map(data.publicationDate)
    )
}
