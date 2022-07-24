package com.yoloroy.retrofit.news.model

import com.yoloroy.domain.model.NewsDetails
import com.yoloroy.domain.model.NewsShort
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.*

interface Article {
    val author: String?
    val content: String?
    val description: String
    val publishedAt: String
    val source: Source
    val title: String
    val url: String
    val urlToImage: String?

    fun dataClass() = DataClass(author, content, description, publishedAt, source, title, url, urlToImage)
    fun short(): NewsShort = Short(this)
    fun details(): NewsDetails = Details(this)

    @Serializable
    data class DataClass(
        override val author: String?,
        override val content: String?,
        override val description: String,
        override val publishedAt: String,
        override val source: Source,
        override val title: String,
        override val url: String,
        override val urlToImage: String?
    ) : Article

    class Short(private val article: Article) : NewsShort {
        override val title get() = article.title
        override val description get() = article.description
        override val imageUrl get() = article.urlToImage
        override val publicationDate: Calendar get() = calendarFromApiString(article.publishedAt)

        internal fun details() = Details(article)
    }

    class Details(private val article: Article) : NewsDetails {
        override val title get() = article.title
        override val imageUrl get() = article.urlToImage
        override val content get() = article.content
        override val publicationDate: Calendar get() = calendarFromApiString(article.publishedAt)

        internal fun short() = Short(article)
    }
}

internal fun calendarFromApiString(apiDate: String) = Calendar.getInstance()
    .apply {
        val format = SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ", Locale.ENGLISH)
        val date = format.parse(apiDate)!!
        time = date
    }
