package com.yoloroy.newsapp.ui.mapper

import com.yoloroy.domain.model.NewsDetails
import com.yoloroy.newsapp.ui.model.NewsDetailsUi
import java.util.*

class NewsDetailsToUiMapper(
    private val dateMapper: Mapper<Calendar, String>
) : Mapper<NewsDetails, NewsDetailsUi> {

    override fun map(data: NewsDetails) = NewsDetailsUi(
        data.title,
        data.imageUrl,
        data.content ?: "",
        dateMapper.map(data.publicationDate)
    )
}
