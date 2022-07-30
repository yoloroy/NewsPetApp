package com.yoloroy.newsapp.ui.news_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yoloroy.newsapp.R
import com.yoloroy.newsapp.databinding.FragmentNewsListBinding
import com.yoloroy.newsapp.util.collections.swapKeysAndValues
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private val viewModel: NewsListViewModel by viewModels()
    private val toolbarItemsManager by lazy { ToolbarItemsManager() }

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: NewsListRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)
        val binder = FragmentNewsListBinding.bind(view)

        toolbar = binder.toolbar
        recyclerView = binder.list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = NewsListRecyclerViewAdapter(emptyList()).also { adapter ->
                recyclerViewAdapter = adapter
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setOnMenuItemClickListener(toolbarItemsManager)
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
        observePredicates()
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

    private fun observePredicates() {
        lifecycleScope.launch {
            Log.i(tag, "start observing predicates")
            viewModel.predicates
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collectLatest { it.map(toolbarItemsManager::setChecked) }
        }
        lifecycleScope.launch {
            Log.i(tag, "start observing predicates")
            viewModel.availablePredicates
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collectLatest { it.map(toolbarItemsManager::setAvailable) }
        }
    }

    private inner class ToolbarItemsManager : Toolbar.OnMenuItemClickListener {
        val titleContainsItem: MenuItem by lazy { toolbar.menu.findItem(R.id.titleContainsMenuItem) }
        val descriptionContainsItem: MenuItem by lazy { toolbar.menu.findItem(R.id.descriptionContainsMenuItem) }
        val contentContainsItem: MenuItem by lazy { toolbar.menu.findItem(R.id.contentContainsMenuItem) }

        private val itemsAndPredicates = with(viewModel) {
            mapOf(
                titleContainsItem to titleContainsPredicateUi,
                descriptionContainsItem to descriptionContainsPredicateUi,
                contentContainsItem to contentContainsPredicateUi
            )
        }
        private val predicatesAndItems = itemsAndPredicates.swapKeysAndValues()

        override fun onMenuItemClick(item: MenuItem?): Boolean = try {
            viewModel.togglePredicate(getPredicateUiFor(item))
            true
        } catch (e: IllegalArgumentException) {
            false
        }

        fun getPredicateUiFor(item: MenuItem?) = item
            ?.let { itemsAndPredicates[item] }
            ?: throw IllegalArgumentException("There no matching menu item")

        fun setAvailable(newsPredicateUi: NewsPredicateUi) {
            predicatesAndItems[newsPredicateUi]
                ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
                ?: throw IllegalArgumentException("There no matching menu item")
        }

        fun setChecked(newsPredicateUi: NewsPredicateUi) {
            predicatesAndItems[newsPredicateUi]
                ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS or MenuItem.SHOW_AS_ACTION_WITH_TEXT)
                ?: throw IllegalArgumentException("There no matching menu item")
        }
    }

    companion object {
        const val tag = "NewsListF"
    }
}
