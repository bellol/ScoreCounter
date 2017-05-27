package com.bellng.scorecounter.dashboard

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import butterknife.BindView
import com.bellng.scorecounter.R
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Bell on 24-May-17.
 */

class DashboardActivity : LifecycleActivity() {

    @BindView(R.id.recycler_view) lateinit var recyclerView: RecyclerView
    private val viewModel by lazy { ViewModelProviders.of(this).get(DashboardViewModel::class.java) }
    private val compositeDisposable = CompositeDisposable()

    var adapter = CounterAdapter()

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.bellng.scorecounter.R.layout.activity_main)
        butterknife.ButterKnife.bind(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable.add(viewModel.counters()
                .subscribe(adapter::update))

        compositeDisposable.add(adapter.onPlusClicked()
                .subscribe(viewModel::incrementCounter))

        compositeDisposable.add(adapter.onMinusClicked()
                .subscribe(viewModel::decrementCounter))
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }
}