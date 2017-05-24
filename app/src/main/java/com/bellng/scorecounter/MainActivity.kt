package com.bellng.scorecounter

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Bell on 24-May-17.
 */

class MainActivity : LifecycleActivity() {

    @BindView(R.id.top_minus) lateinit var topMinusButton: Button
    @BindView(R.id.top_plus) lateinit var topPlusButton: Button
    @BindView(R.id.top_count) lateinit var topCount: TextView

    @BindView(R.id.bottom_minus) lateinit var bottomMinusButton: Button
    @BindView(R.id.bottom_plus) lateinit var bottomPlusButton: Button
    @BindView(R.id.bottom_count) lateinit var bottomCount: TextView

    private val disposables = CompositeDisposable()
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
    }

    override fun onResume() {
        super.onResume()
        disposables.add(viewModel.topCount().subscribe { topCount.text = it.toString() })

        disposables.add(viewModel.bottomCount().subscribe { bottomCount.text = it.toString() })

        disposables.add(topPlusButton.clicks().subscribe { viewModel.onTopPlusClicked() })
        disposables.add(topMinusButton.clicks().subscribe { viewModel.onTopMinusClicked() })

        disposables.add(bottomPlusButton.clicks().subscribe { viewModel.onBottomPlusClicked() })
        disposables.add(bottomMinusButton.clicks().subscribe { viewModel.onBottomMinusClicked() })
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }
}