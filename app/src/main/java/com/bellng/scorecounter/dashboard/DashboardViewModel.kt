package com.bellng.scorecounter.dashboard

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Bell on 27-May-17.
 */
class DashboardViewModel : ViewModel() {
    private var counters: MutableList<Counter> = ArrayList()
    private val counterSubject = BehaviorSubject.create<Pair<Int,List<Counter>>>()

    init {
        counters.add(Counter())
        counters.add(Counter(colourString = "#FF4081"))
        counters.add(Counter(colourString = "#303F9F"))

        counterSubject.onNext((-1).to(counters))
    }

    fun counters(): Observable<Pair<Int,List<Counter>>> = counterSubject

    fun incrementCounter(index: Int) {
        counters[index].count++
        counterSubject.onNext(index.to(counters))
    }

    fun decrementCounter(index: Int) {
        counters[index].count--
        counterSubject.onNext(index.to(counters))
    }
}