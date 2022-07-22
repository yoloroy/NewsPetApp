package com.yoloroy.newsapp.ui.mapper.model_mapper

import com.yoloroy.domain.model.NewsShort
import com.yoloroy.newsapp.ui.mapper.Mapper
import com.yoloroy.newsapp.ui.model.NewsShortUi
import java.util.*

class NewsShortToUiMapper(
    private val dateMapper: Mapper<Calendar, String>
) : Mapper<NewsShort, NewsShortUi> {

    override fun map(data: NewsShort): NewsShortUi = NewsShortUi(
        data.imageUrl,
        data.title,
        dateMapper.map(data.publicationDate),
        data.description
    )
}
