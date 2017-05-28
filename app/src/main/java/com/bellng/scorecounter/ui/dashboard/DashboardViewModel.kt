package com.bellng.scorecounter.ui.dashboard

import android.arch.lifecycle.ViewModel
import com.bellng.scorecounter.model.Counter
import com.bellng.scorecounter.model.CounterModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Bell on 27-May-17.
 */
class DashboardViewModel(val counterModel: CounterModel) : ViewModel() {

    private val showResetDialog = PublishSubject.create<Unit>()
    private val showEditScreen = PublishSubject.create<Unit>()

    fun countChanges(): Observable<Pair<List<Counter>, Int>> = counterModel.countChanges()

    fun counterListChanges(): Observable<List<Counter>> = counterModel.counterListChanges()

    fun showResetDialog(): Observable<Unit> = showResetDialog

    fun showEditScreen(): Observable<Unit> = showEditScreen

    fun onIncrementCounter(index: Int) = counterModel.incrementCounter(index)

    fun onDecrementCounter(index: Int) = counterModel.decrementCounter(index)

    fun onResetCountersDialogAccepted() = counterModel.resetCounters()

    fun onEditClicked() = showEditScreen.onNext(Unit)

    fun onResetCounters() = showResetDialog.onNext(Unit)
}