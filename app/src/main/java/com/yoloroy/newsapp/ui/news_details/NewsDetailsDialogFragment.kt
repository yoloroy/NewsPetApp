package com.yoloroy.newsapp.ui.news_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.yoloroy.newsapp.R
import com.yoloroy.newsapp.databinding.FragmentNewsDetailsBinding
import com.yoloroy.newsapp.ui.model.NewsShortUi
import com.yoloroy.newsapp.ui.util.glide.addListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsDetailsDialogFragment(
    private val item: NewsShortUi
) : DialogFragment() {

    private val viewModel: NewsDetailsViewModel by viewModels()

    private lateinit var imageView: ImageView
    private lateinit var toolbar: Toolbar
    private lateinit var titleView: TextView
    private lateinit var publicationDateView: TextView
    private lateinit var contentView: TextView

    companion object {
        private const val tag = "NewsDetailsF"

        fun display(fragmentManager: FragmentManager, short: NewsShortUi) = NewsDetailsDialogFragment(short).apply {
            show(fragmentManager, this@Companion.tag)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_news_details, container, false).also { view ->
            val binder = FragmentNewsDetailsBinding.bind(view)
            imageView = binder.image
            toolbar = binder.toolbar
            titleView = binder.title
            publicationDateView = binder.publicationDate
            contentView = binder.content
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { dismiss() }
    }

    override fun onStart() {
        super.onStart()
        viewModel.init(item)
        observeViewModel()
        requireDialog().window!!.setLayout(MATCH_PARENT, MATCH_PARENT)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.newsDetailsUi.collect { newsDetails ->
                loadImage(newsDetails.imageUrl)
                titleView.text = newsDetails.title
                publicationDateView.text = newsDetails.publicationDate
                contentView.text = newsDetails.content
            }
        }
    }

    private fun loadImage(imageUrl: String?) {
        Glide.with(context!!)
            .load(imageUrl)
            .addListener(
                onReady = { _, _, _, _, _ -> imageView.visibility = View.VISIBLE; false },
                onFail = { _, _, _, _ -> imageView.visibility = View.GONE; false }
            )
            .into(imageView)
    }
}
