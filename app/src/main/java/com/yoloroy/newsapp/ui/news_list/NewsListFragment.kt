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
import com.yoloroy.newsapp.ui.news_list.NewsPredicateUi.PredicateStatus
import com.yoloroy.newsapp.ui.news_list.predicate.contains.NewsPredicateContainsDialogFragment
import com.yoloroy.newsapp.util.collections.swapKeysAndValues
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private val viewModel: NewsListViewModel by viewModels()
    private val toolbarPredicatesManager by lazy { ToolbarPredicatesManager() }

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

        toolbar.setOnMenuItemClickListener(toolbarPredicatesManager)
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
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { it.forEach(toolbarPredicatesManager::apply) }
        }
    }

    private fun startPredicateDialog(predicate: NewsPredicateUi) {
        val predicateDialogTag = tag + "_PD"
        NewsPredicateContainsDialogFragment(
            isInitial = predicate.status == PredicateStatus.Available,
            onAdd = {
                Log.i(predicateDialogTag, "onAdd before")
                viewModel.applyPredicate(predicate, it)
                Log.i(predicateDialogTag, "onAdd after")
            },
            onCancelAdding = {
                Log.i(predicateDialogTag, "onCancelAdding")
            },
            onUpdate = {
                Log.i(predicateDialogTag, "onUpdate before")
                viewModel.applyPredicate(predicate, it)
                Log.i(predicateDialogTag, "onUpdate after")
           },
            onCancelUpdating = {
               Log.i(predicateDialogTag, "onCancelUpdating")
            },
            onRemove = {
                Log.i(predicateDialogTag, "onRemove before")
                viewModel.removePredicate(predicate)
                Log.i(predicateDialogTag, "onRemove after")
           },
            type = predicate
        ).show(childFragmentManager, tag)
    }

    private inner class ToolbarPredicatesManager : Toolbar.OnMenuItemClickListener {
        val titleContainsItem: MenuItem by lazy { toolbar.menu.findItem(R.id.titleContainsMenuItem) }
        val descriptionContainsItem: MenuItem by lazy { toolbar.menu.findItem(R.id.descriptionContainsMenuItem) }
        val contentContainsItem: MenuItem by lazy { toolbar.menu.findItem(R.id.contentContainsMenuItem) }

        private val itemsWithTypes = with(viewModel) {
            mapOf(
                titleContainsItem to titleContainsPredicateUi.type,
                descriptionContainsItem to descriptionContainsPredicateUi.type,
                contentContainsItem to contentContainsPredicateUi.type
            )
        }
        private val predicatesAndItems get() = itemsWithTypes.swapKeysAndValues()

        override fun onMenuItemClick(item: MenuItem?): Boolean = try {
            startPredicateDialog(itemsWithTypes[item!!]!!.uiState())
            true
        } catch (e: IllegalArgumentException) {
            Toast.makeText(context, R.string.unknown_problem, Toast.LENGTH_LONG)
            false
        } catch (e: NullPointerException) {
            Toast.makeText(context, R.string.unknown_problem, Toast.LENGTH_LONG)
            false
        }

        fun apply(state: NewsPredicateUi) {
            predicatesAndItems
                .toList()
                .first { (type, _) -> type == state.type }
                .second.let { state.applyToMenuItem(it) }
        }
    }

    companion object {
        const val tag = "NewsListF"
    }

    private fun NewsPredicateUi.Type.uiState() = viewModel.predicates.value.first { it.type == this }
}

fun NewsPredicateUi.applyToMenuItem(item: MenuItem) = item.apply {
    val showAsAction = when (status) {
        PredicateStatus.Available -> MenuItem.SHOW_AS_ACTION_NEVER
        PredicateStatus.Applied -> MenuItem.SHOW_AS_ACTION_ALWAYS or MenuItem.SHOW_AS_ACTION_WITH_TEXT
    }
    setShowAsAction(showAsAction)
    title = filterName
}
