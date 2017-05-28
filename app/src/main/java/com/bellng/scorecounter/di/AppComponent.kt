package com.bellng.scorecounter.di

import com.bellng.scorecounter.dashboard.DashboardActivity
import com.bellng.scorecounter.dashboard.DashboardViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Bell on 28-May-17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun getDashboardViewModel(): DashboardViewModel

    fun inject(dashboardActivity: DashboardActivity)
}