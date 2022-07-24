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

        observeViewModel()
    }

    private fun observeViewModel() {
        Log.i(tag, "start observing VM")
        observeNews()
        observeLoading()
        observeProblems()
    }

    private fun observeNews() {
        lifecycleScope.launch {
            Log.i(tag, "start observing VM.news")
            viewModel.news
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collectLatest {
                    Log.i(tag, it.joinToString(",\n", "[\n", "]"))
                    recyclerViewAdapter.updateValues(it)
                }
        }
    }

    private fun observeLoading() {
        lifecycleScope.launch {
            Log.i(tag, "start observing VM.isLoading")
            viewModel.isLoading
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collectLatest { isLoading ->
                    Log.i(tag, "loading: $isLoading")
                    if (isLoading) {
                        Toast.makeText(context, "\uD83D\uDD03", Toast.LENGTH_SHORT).show() // 🔃
                    }
                }
        }
    }

    private fun observeProblems() {
        lifecycleScope.launch {
            Log.i(tag, "start observing VM.problem")
            viewModel.problem
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collectLatest { problem ->
                    if (problem != null) {
                        Toast.makeText(context, problem, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    companion object {
        const val tag = "NewsListF"
    }
}