package com.bellng.scorecounter.model

import android.content.SharedPreferences
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

/**
 * Created by Bell on 28-May-17.
 */
class CounterStorage(private val sharedPreferences: SharedPreferences) {

    private val type: Type = Types.newParameterizedType(List::class.java, Counter::class.java)
    private val adapter: JsonAdapter<List<Counter>> = Moshi.Builder().build().adapter(type)

    fun saveCounters(counterList: List<Counter>) {
        val serialised = adapter.toJson(counterList)
        sharedPreferences.edit().putString("counters_key", serialised).apply()
    }

    fun getCounters(): List<Counter> {
        val serialised = sharedPreferences.getString("counters_key", null) ?: return ArrayList()
        return adapter.fromJson(serialised) ?: return ArrayList()
    }

}