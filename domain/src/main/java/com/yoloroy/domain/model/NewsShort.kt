package com.yoloroy.domain.model

import java.util.*

interface NewsShort {
    val title: String
    val description: String
    val imageUrl: String?
    val publicationDate: Calendar

    data class Base(
        override val title: String,
        override val description: String,
        override val imageUrl: String?,
        override val publicationDate: Calendar
    ) : NewsShort
}
