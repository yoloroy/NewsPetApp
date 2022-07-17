package com.yoloroy.room.news

import com.yoloroy.retrofit.news.model.Article
import com.yoloroy.room.db.dao.ArticleDao
import com.yoloroy.room.news.model.ArticleWithId

interface ArticleSource {

    fun getById(id: Long): ArticleWithId

    fun insert(vararg articles: Article): List<ArticleWithId>

    class Base(private val articleDao: ArticleDao) : ArticleSource {

        override fun getById(id: Long): ArticleWithId = articleDao
            .getById(id)
            .toModel()

        override fun insert(vararg articles: Article): List<ArticleWithId> {
            val articlesEntities = articles.map(Article::toEntity)
            val articlesIds = articleDao.insert(*articlesEntities.toTypedArray())
            return (articlesIds zip articles)
                .map { (id, article) -> ArticleWithId(id, article) }
        }
    }
}
