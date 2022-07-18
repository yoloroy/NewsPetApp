package com.yoloroy.newsapp.ui.mapper

import com.yoloroy.domain.model.NewsShort
import com.yoloroy.newsapp.ui.model.NewsShortUi
import java.util.*

class NewsShortToUiMapper(locale: Locale) : Mapper<NewsShort, NewsShortUi> {
    private val dateMapper = CalendarToStringMapper(locale)

    override fun map(data: NewsShort): NewsShortUi = NewsShortUi(
        data.imageUrl,
        data.title,
        dateMapper.map(data.publicationDate),
        data.description
    )
}
