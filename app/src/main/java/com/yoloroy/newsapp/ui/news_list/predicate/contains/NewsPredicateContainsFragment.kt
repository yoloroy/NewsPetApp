package com.yoloroy.newsapp.ui.news_list.predicate.contains

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.yoloroy.newsapp.R
import com.yoloroy.newsapp.ui.news_list.NewsPredicateUi

open class NewsPredicateContainsDialogFragment(
    private val isInitial: Boolean,
    private val onAdd: (String) -> Unit,
    private val onCancelAdding: () -> Unit,
    private val onUpdate: (String) -> Unit,
    private val onCancelUpdating: () -> Unit,
    private val onRemove: () -> Unit,
    private val name: String,
    private val description: String,
    private var fieldData: String = ""
) : DialogFragment() {

    constructor(
        isInitial: Boolean,
        onAdd: (String) -> Unit,
        onCancelAdding: () -> Unit,
        onUpdate: (String) -> Unit,
        onCancelUpdating: () -> Unit,
        onRemove: () -> Unit,
        type: NewsPredicateUi
    ) : this(
        isInitial,
        onAdd,
        onCancelAdding,
        onUpdate,
        onCancelUpdating,
        onRemove,
        type.filterName,
        type.description,
        type.fieldData
    )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(activity)
            .setTitle(name)
            .setView(EditText(context).apply {
                hint = description
                inputType = InputType.TYPE_CLASS_TEXT
                setText(fieldData)
                doOnTextChanged { text, _, _, _ -> fieldData = text.toString() }
            })
            .setNewsPredicateActions()
            .show()

    private fun AlertDialog.Builder.setNewsPredicateActions(): AlertDialog.Builder =
        if (isInitial) setNewsPredicateInitialActions()
        else setNewsPredicateLaterActions()

    private fun AlertDialog.Builder.setNewsPredicateInitialActions(): AlertDialog.Builder = this
        .setPositiveButton(R.string.action_add) { _, _ -> onAdd(fieldData) }
        .setNegativeButton(R.string.action_cancel) { _, _ -> onCancelAdding() }
        .setOnDismissListener { onCancelAdding()  }

    private fun AlertDialog.Builder.setNewsPredicateLaterActions(): AlertDialog.Builder = this
        .setPositiveButton(R.string.action_update) { _, _ -> onUpdate(fieldData) }
        .setNeutralButton(R.string.action_cancel) { _, _ -> onCancelUpdating() }
        .setNegativeButton(R.string.action_remove) { _, _ -> onRemove() }
        .setOnDismissListener { onCancelUpdating() }
}
