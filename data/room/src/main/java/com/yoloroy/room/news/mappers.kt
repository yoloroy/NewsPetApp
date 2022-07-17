package com.yoloroy.room.news

import com.yoloroy.retrofit.news.model.Article
import com.yoloroy.retrofit.news.model.Source
import com.yoloroy.room.db.entity.ArticleEntity
import com.yoloroy.room.news.model.ArticleWithId

fun ArticleEntity.toModel() = ArticleWithId(
    articleId,
    Article.DataClass(
        author,
        content,
        description,
        publishedAt,
        Source(
            sourceId,
            sourceName
        ),
        title,
        url,
        urlToImage
    )
)

fun Article.toEntity() = ArticleEntity(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    sourceId = source.id,
    sourceName = source.name,
    title = title,
    url = url,
    urlToImage = urlToImage
)
