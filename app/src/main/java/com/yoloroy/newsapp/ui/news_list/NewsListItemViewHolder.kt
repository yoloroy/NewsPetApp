package com.yoloroy.newsapp.ui.news_list

import android.view.View
import android.widget.ImageView
import android.widget.Space
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoloroy.newsapp.databinding.FragmentNewsListItemBinding
import com.yoloroy.newsapp.ui.model.NewsShortUi
import com.yoloroy.newsapp.ui.util.glide.addListener

class NewsListItemViewHolder(
    binding: FragmentNewsListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val imageView: ImageView = binding.image
    private val titleView: TextView = binding.title
    private val publicationDateView: TextView = binding.publicationDate
    private val descriptionView: TextView = binding.description

    private val imgSpace: Space = binding.imgSpace
    private val noImgSpace: Space = binding.noImgSpace

    fun bind(item: NewsShortUi) {
        bindImage(item)
        titleView.text = item.title
        publicationDateView.text = item.publicationDate
        descriptionView.text = item.description
    }

    private fun bindImage(item: NewsShortUi) {
        imageView.visibility = View.INVISIBLE
        imgSpace.visibility = View.GONE
        noImgSpace.visibility = View.VISIBLE

        Glide.with(itemView.context)
            .load(item.imageUrl)
            .addListener(
                onReady = { _, _, _, _, _ ->
                    imageView.visibility = View.VISIBLE
                    imgSpace.visibility = View.VISIBLE
                    noImgSpace.visibility = View.GONE
                    false
                }
            )
            .into(imageView)
    }

    override fun toString() = "${super.toString()} '${descriptionView.text}'"
}
