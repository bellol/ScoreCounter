package com.bellng.scorecounter

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Bell on 24-May-17.
 */

class MainViewModel : ViewModel() {

    private var topCount = 0
    private val topCountSubject = BehaviorSubject.create<Int>()
    private var bottomCount = 0
    private val bottomCountSubject = BehaviorSubject.create<Int>()

    fun topCount(): Observable<Int> = topCountSubject
    fun bottomCount(): Observable<Int> = bottomCountSubject

    fun onTopPlusClicked() = topCountSubject.onNext(++topCount)
    fun onTopMinusClicked() = topCountSubject.onNext(--topCount)

    fun onBottomPlusClicked() = bottomCountSubject.onNext(++bottomCount)
    fun onBottomMinusClicked() = bottomCountSubject.onNext(--bottomCount)
}
