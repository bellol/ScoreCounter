package com.bellng.scorecounter.dashboard

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.Menu
import android.view.MenuItem
import butterknife.BindView
import com.bellng.scorecounter.R
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Bell on 24-May-17.
 */

class DashboardActivity : AppCompatActivity() {

    @BindView(R.id.recycler_view) lateinit var recyclerView: RecyclerView

    private val viewModel by lazy { ViewModelProviders.of(this).get(DashboardViewModel::class.java) }
    private val disposables = CompositeDisposable()

    private var adapter = CounterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        butterknife.ButterKnife.bind(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.adapter = adapter
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

        disposables.add(viewModel.counterListChanges()
                .subscribe {
                    adapter.counterList = it
                    recyclerView.adapter = adapter
                })

        disposables.add(viewModel.countChanges()
                .subscribe {
                    adapter.counterList = it.first
                    adapter.notifyItemChanged(it.second)
                })

        disposables.add(viewModel.showEditScreen()
                .subscribe { showEditScreen() })

        disposables.add(viewModel.showResetDialog()
                .subscribe { showResetDialog() })

        disposables.add(adapter.onPlusClicked()
                .subscribe(viewModel::onIncrementCounter))

        disposables.add(adapter.onMinusClicked()
                .subscribe(viewModel::onDecrementCounter))
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

    private fun showEditScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showResetDialog() {
        AlertDialog.Builder(this)
                .setMessage("Reset all counters?")
                .setPositiveButton("Reset") { dialog, _ ->
                    viewModel.onResetCountersDialogAccepted()
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()

    }
}