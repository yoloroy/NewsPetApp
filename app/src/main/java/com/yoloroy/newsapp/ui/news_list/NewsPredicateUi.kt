package com.yoloroy.newsapp.ui.news_list

import android.content.Context
import androidx.annotation.StringRes
import com.yoloroy.domain.model.NewsPredicate
import com.yoloroy.newsapp.R

data class NewsPredicateUi(
    val status: PredicateStatus,
    val type: Type,
    val filterName: String,
    val description: String,
    val fieldData: String = ""
) {

    fun toPredicate(): NewsPredicate = when (type) {
        Type.TitleContains -> NewsPredicate.TitleContains(fieldData)
        Type.DescriptionContains -> NewsPredicate.DescriptionContains(fieldData)
        Type.ContentContains -> NewsPredicate.ContentContains(fieldData)
    }

    class ResProducer(private val context: Context) {

        fun produce(type: Type) = NewsPredicateUi(
            PredicateStatus.Available,
            type = type.type,
            filterName = context.getString(type.nameId),
            description = context.getString(type.descriptionId)
        )

        enum class Type(
            val type: NewsPredicateUi.Type,
            @StringRes val nameId: Int,
            @StringRes val descriptionId: Int
        ) {
            TitleContains(
                NewsPredicateUi.Type.TitleContains,
                R.string.title_contains_name,
                R.string.title_contains_description
            ),
            DescriptionContains(
                NewsPredicateUi.Type.DescriptionContains,
                R.string.description_contains_name,
                R.string.description_contains_description
            ),
            ContentContains(
                NewsPredicateUi.Type.ContentContains,
                R.string.content_contains_name,
                R.string.content_contains_description
            )
        }
    }

    enum class Type { TitleContains, DescriptionContains, ContentContains }
    enum class PredicateStatus { Available, Applied }
}
