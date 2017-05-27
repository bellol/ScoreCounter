package com.bellng.scorecounter.dashboard

import android.arch.lifecycle.ViewModel
import com.bellng.scorecounter.model.Counter
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 * Created by Bell on 27-May-17.
 */
class DashboardViewModel : ViewModel() {
    private val countChange = PublishSubject.create<Pair<List<Counter>, Int>>()
    private val counterList = BehaviorSubject.create<List<Counter>>()
    private val showResetDialog = PublishSubject.create<Unit>()
    private val showEditScreen = PublishSubject.create<Unit>()

    private var counters: MutableList<Counter> = ArrayList()

    init {
        counters.add(Counter())
        counters.add(Counter(colourString = "#FF4081"))
        counters.add(Counter(colourString = "#303F9F"))

        counterList.onNext(counters)
    }

    fun countChanges(): Observable<Pair<List<Counter>, Int>> = countChange

    fun counterListChanges(): Observable<List<Counter>> = counterList

    fun showResetDialog(): Observable<Unit> = showResetDialog

    fun showEditScreen(): Observable<Unit> = showEditScreen

    fun onIncrementCounter(index: Int) {
        counters[index].count++
        countChange.onNext(counters.to(index))
    }

    fun onDecrementCounter(index: Int) {
        counters[index].count--
        countChange.onNext(counters.to(index))
    }

    fun onResetCountersDialogAccepted() {
        counters.forEach { it.count = 0 }
        counterList.onNext(counters)
    }

    fun onEditClicked() {
        showEditScreen.onNext(Unit)
    }

    fun onResetCounters() {
        showResetDialog.onNext(Unit)
    }
}