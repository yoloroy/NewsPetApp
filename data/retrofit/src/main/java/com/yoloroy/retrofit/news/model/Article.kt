package com.yoloroy.retrofit.news.model

import com.yoloroy.domain.model.NewsDetails
import com.yoloroy.domain.model.NewsShort
import java.text.SimpleDateFormat
import java.util.*

data class Article(
    val author: String?,
    val content: String?,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String?
) {
    inner class Short : NewsShort {
        override val title get() = this@Article.title
        override val description get() = this@Article.description
        override val imageUrl get() = this@Article.urlToImage
        override val publicationDate: Calendar get() = calendarFromApiString(publishedAt)

        internal fun details() = Details()
    }

    inner class Details : NewsDetails {
        override val title get() = this@Article.title
        override val imageUrl get() = this@Article.urlToImage
        override val content get() = this@Article.content
        override val publicationDate: Calendar get() = calendarFromApiString(publishedAt)

        internal fun short() = Short()
    }
}

internal fun calendarFromApiString(apiDate: String) = Calendar.getInstance()
    .apply {
        val format = SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ", Locale.ENGLISH)
        val date = format.parse(apiDate)!!
        time = date
    }
