package com.yoloroy.newsapp.ui.mapper.model_mapper

import android.util.Log
import com.yoloroy.domain.model.NewsShort
import com.yoloroy.newsapp.ui.mapper.Mapper
import com.yoloroy.newsapp.ui.model.NewsShortUi
import java.util.*

class NewsShortToUiMapper(
    private val dateMapper: Mapper<Calendar, String>
) : Mapper<NewsShort, NewsShortUi> {

    private val map = mutableSetOf<Triple<String, NewsShort, NewsShortUi>>() // TODO refactor

    override fun map(data: NewsShort): NewsShortUi = NewsShortUi(
        data.imageUrl,
        data.title,
        dateMapper.map(data.publicationDate),
        data.description
    ).also {
        map += Triple(data.toString() + it.toString(), data, it)
        Log.i("NSMapper", map.toString())
    }

    inner class NewsShortUiToModelMapper : Mapper<NewsShortUi, NewsShort> {

        override fun map(data: NewsShortUi): NewsShort = map
            .also { Log.i("NSMapper", it.toString()) }
            .first { (code, _, newsShortUi) -> newsShortUi == data && newsShortUi.toString() in code }
            .also { Log.i("NSMapper", it.toString()) }
            .let { (_, newsShort, _) -> newsShort }
            .also { Log.i("NSMapper", it.toString()) }
    }
}
