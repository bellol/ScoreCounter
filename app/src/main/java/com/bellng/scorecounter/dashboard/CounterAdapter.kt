package com.bellng.scorecounter.dashboard

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bellng.scorecounter.R
import com.bellng.scorecounter.model.Counter
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.view.detaches
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.counter_layout.view.*

/**
 * Created by Bell on 27-May-17.
 */
class CounterAdapter(counterList: List<Counter> = ArrayList()) : RecyclerView.Adapter<CounterAdapter.ViewHolder>() {

    val plusSubject: PublishSubject<Int> = PublishSubject.create<Int>()
    val minusSubject: PublishSubject<Int> = PublishSubject.create<Int>()

    var counterList = counterList

    fun onPlusClicked(): Observable<Int> = plusSubject

    fun onMinusClicked(): Observable<Int> = minusSubject

    override fun getItemCount() = counterList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.counter_layout, parent, false)

        val height = parent.measuredHeight / counterList.size
        val width = parent.measuredWidth

        view.layoutParams = ViewGroup.LayoutParams(width, height)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val counter = counterList[position]

        holder.count.text = counter.count.toString()
        holder.itemView.setBackgroundColor(Color.parseColor(counter.colourString))

        holder.plusButton.clicks()
                .map { position }
                .takeUntil(holder.plusButton.detaches())
                .subscribe(plusSubject::onNext)

        holder.minusButton.clicks()
                .map { position }
                .takeUntil(holder.minusButton.detaches())
                .subscribe(minusSubject::onNext)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val count: TextView = itemView.count
        val plusButton: Button = itemView.plus_button
        val minusButton: TextView = itemView.minus_button
    }
}