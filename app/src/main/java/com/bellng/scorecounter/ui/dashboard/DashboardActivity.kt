package com.bellng.scorecounter.ui.dashboard

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.view.Menu
import android.view.MenuItem
import com.bellng.scorecounter.CounterApp
import com.bellng.scorecounter.R
import com.bellng.scorecounter.ui.edit.EditActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert


/**
 * Created by Bell on 24-May-17.
 */

class DashboardActivity : AppCompatActivity() {

    private val viewModelFactory by lazy { (application as CounterApp).appComponent.getViewModelFactory() }
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java) }
    private val disposables = CompositeDisposable()

    private var adapter = CounterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        (recycler_view.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recycler_view.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.edit_counters -> viewModel.onEditClicked()
            R.id.reset_all_counters -> viewModel.onResetCounters()
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    override fun onResume() {
        super.onResume()

        disposables.addAll(
                viewModel.counterListChanges()
                        .subscribe {
                            adapter.counterList = it
                            recycler_view.adapter = adapter
                        },
                viewModel.countChanges()
                        .subscribe {
                            adapter.counterList = it.first
                            adapter.notifyItemChanged(it.second)
                        },
                viewModel.showEditScreen()
                        .subscribe {
                            showEditScreen()
                        },
                viewModel.showResetDialog()
                        .subscribe {
                            showResetDialog()
                        },
                adapter.onPlusClicked()
                        .subscribe(viewModel::onIncrementCounter),
                adapter.onMinusClicked()
                        .subscribe(viewModel::onDecrementCounter)
        )
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

    private fun showEditScreen() = startActivity(Intent(this, EditActivity::class.java))

    private fun showResetDialog() {
        alert("Reset all counters to 0?") {
            positiveButton("Reset") { viewModel.onResetCountersDialogAccepted() }
            negativeButton("Cancel") {}
        }.show()
    }
}