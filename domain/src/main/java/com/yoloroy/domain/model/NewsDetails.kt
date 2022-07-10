package com.yoloroy.domain.model

import java.util.*

interface NewsDetails {
    val title: String
    val imageUrl: String
    val content: String
    val publicationDate: Calendar

    data class Base(
        override val title: String,
        override val imageUrl: String,
        override val content: String,
        override val publicationDate: Calendar
    ) : NewsDetails
}
