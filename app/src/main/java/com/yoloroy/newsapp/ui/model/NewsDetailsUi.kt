package com.yoloroy.newsapp.ui.model

data class NewsDetailsUi(
    val title: String,
    val imageUrl: String?,
    val content: String = "",
    val publicationDate: String
)
