package com.yoloroy.newsapp.ui.news_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Space
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoloroy.newsapp.databinding.FragmentNewsListItemBinding
import com.yoloroy.newsapp.ui.model.NewsShortUi

class NewsListRecyclerViewAdapter(
    private var values: List<NewsShortUi>
) : RecyclerView.Adapter<NewsListRecyclerViewAdapter.ViewHolder>() {

    fun updateValues(values: List<NewsShortUi>) {
        this.values = values
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentNewsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentNewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val imgSpace: Space = binding.imgSpace
        private val imageView: ImageView = binding.image
        private val titleView: TextView = binding.title
        private val publicationDateView: TextView = binding.publicationDate
        private val descriptionView: TextView = binding.description

        fun bind(item: NewsShortUi) {
            if (item.imageUrl != null) {
                Glide.with(itemView.context)
                    .load(item.imageUrl)
                    .into(imageView)
                imgSpace.visibility = View.VISIBLE
            } else {
                imgSpace.visibility = View.GONE
            }
            titleView.text = item.title
            publicationDateView.text = item.publicationDate
            descriptionView.text = item.description
        }

        override fun toString(): String {
            return "${super.toString()} '${descriptionView.text}'"
        }
    }
}
