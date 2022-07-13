package com.yoloroy.domain.model

import java.util.function.Predicate

sealed interface NewsPredicate : Predicate<NewsFilterData> {

    override fun test(data: NewsFilterData): Boolean

    class List(private val predicates: Collection<NewsPredicate>) : NewsPredicate {
        override fun test(data: NewsFilterData): Boolean = predicates.all { it.test(data) }

        override fun plus(other: NewsPredicate) = List(predicates + other)
    }

    sealed class Contains(
        private val title: String? = null,
        private val description: String? = null,
        private val content: String? = null
    ) : NewsPredicate {
        override fun test(data: NewsFilterData): Boolean = // TODO CHECK refactor
            title?.let { data.title.contains(it) } ?:
            description?.let { data.description.contains(it) } ?:
            content?.let { data.content.contains(it) } ?:
            throw IllegalStateException()
    }

    class TitleContains(title: String) : Contains(title = title)
    class DescriptionContains(description: String) : Contains(description = description)
    class ContentContains(content: String) : Contains(content = content)

    fun <T> apply(list: Collection<T>, map: (T) -> NewsFilterData) = list.filter { test(map(it)) }

    operator fun plus(other: NewsPredicate): NewsPredicate = List(listOf(this, other))
}
