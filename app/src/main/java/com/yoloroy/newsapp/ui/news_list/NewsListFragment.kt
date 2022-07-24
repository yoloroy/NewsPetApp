package com.yoloroy.newsapp.ui.news_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yoloroy.newsapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private val viewModel: NewsListViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: NewsListRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recyclerView = inflater.inflate(R.layout.fragment_news_list, container, false) as RecyclerView
        recyclerView.apply { // TODO refactor
            layoutManager = LinearLayoutManager(context)
            adapter = NewsListRecyclerViewAdapter(emptyList()).also { adapter ->
                recyclerViewAdapter = adapter
            }
        }

        return recyclerView
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch { // TODO refactor
            Log.i(tag, "start observing VM")
            viewModel.news
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    Log.i(tag, it.joinToString(",\n\t", "[\n", "]"))
                    recyclerViewAdapter.updateValues(it)
                }

            viewModel.isLoading
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { isLoading ->
                    Log.i(tag, "loading: $isLoading")
                    if (isLoading) {
                        Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show()
                    }
                }

            viewModel.problem
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { problem -> Toast.makeText(context, problem, Toast.LENGTH_LONG) }
        }
    }

    companion object {
        const val tag = "NewsListF"
    }
}