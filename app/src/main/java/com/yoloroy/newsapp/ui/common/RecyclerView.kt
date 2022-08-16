package com.yoloroy.newsapp.ui.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class MyRecyclerViewViewHolder<T>(
    itemView: View,
    protected open val onItemClickListener: OnItemClickListener<T> = OnItemClickListener { _, _ -> }
) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: T)
}

fun interface OnItemClickListener<T> {

    fun onClickItem(view: View?, item: T)

    context(RecyclerView.ViewHolder)
    fun bind(item: T, view: View = itemView) = view.setOnClickListener { onClickItem(it, item) }
}
