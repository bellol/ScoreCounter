package com.bellng.scorecounter.dagger

import android.app.Application

/**
 * Created by Bell on 28-May-17.
 */
class CounterApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .build()
    }
}