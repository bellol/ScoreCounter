package com.bellng.scorecounter

import android.app.Application
import com.bellng.scorecounter.di.AppComponent
import com.bellng.scorecounter.di.AppModule
import com.bellng.scorecounter.di.DaggerAppComponent

/**
 * Created by Bell on 28-May-17.
 */
class CounterApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}