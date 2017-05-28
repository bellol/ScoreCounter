package com.bellng.scorecounter.model

import com.bellng.scorecounter.CounterStorage
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Bell on 28-May-17.
 */
class CounterModel(private val counterStorage: CounterStorage) {

    private val countChangeSubject = PublishSubject.create<Pair<List<Counter>, Int>>()
    private val counterListSubject = PublishSubject.create<List<Counter>>()

    fun getCounters(): List<Counter> {
        return counterStorage.getCounters()
    }

    fun saveCounters(countersList: List<Counter>) {
        counterStorage.saveCounters(countersList)
    }

    fun countChanges(): Observable<Pair<List<Counter>, Int>> = countChangeSubject

    fun counterListChanges(): Observable<List<Counter>> = counterListSubject.startWith(Observable.just(getCounters()))

    fun incrementCounter(index: Int) {
        val list = getCounters()
        list[index].count++
        saveCounters(list)
        countChangeSubject.onNext(list.to(index))
    }

    fun decrementCounter(index: Int) {
        val list = getCounters()
        list[index].count--
        saveCounters(list)
        countChangeSubject.onNext(list.to(index))
    }

    fun resetCounters() {
        val list = getCounters()
        list.forEach { it.count = 0 }
        saveCounters(list)
        counterListSubject.onNext(list)
    }
}