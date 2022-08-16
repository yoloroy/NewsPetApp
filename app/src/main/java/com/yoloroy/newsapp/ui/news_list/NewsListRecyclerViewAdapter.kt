package com.yoloroy.newsapp.ui.news_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yoloroy.newsapp.databinding.FragmentNewsListItemBinding
import com.yoloroy.newsapp.ui.common.MyRecyclerViewViewHolder
import com.yoloroy.newsapp.ui.common.OnItemClickListener
import com.yoloroy.newsapp.ui.model.NewsShortUi

class NewsListRecyclerViewAdapter(
    private var values: List<NewsShortUi>,
    private val onItemClickListener: OnItemClickListener<NewsShortUi> = OnItemClickListener { _, _ ->  }
) : RecyclerView.Adapter<MyRecyclerViewViewHolder<NewsShortUi>>() {

    fun updateValues(values: List<NewsShortUi>) {
        this.values = values
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsListItemViewHolder(
            FragmentNewsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClickListener
        )

    override fun onBindViewHolder(holder: MyRecyclerViewViewHolder<NewsShortUi>, position: Int) =
        holder.bind(values[position])

    override fun getItemCount(): Int = values.size
}
