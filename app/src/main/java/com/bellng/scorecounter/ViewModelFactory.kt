package com.bellng.scorecounter

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.util.ArrayMap
import com.bellng.scorecounter.dagger.AppComponent
import com.bellng.scorecounter.dashboard.DashboardViewModel

/**
 * Created by Bell on 28-May-17.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(appComponent: AppComponent) : ViewModelProvider.Factory {

    private val creators: ArrayMap<Class<*>, () -> ViewModel> = ArrayMap()

    init {
        creators.put(DashboardViewModel::class.java, { appComponent.getDashboardViewModel() })
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T = creators[modelClass]?.invoke() as T
}