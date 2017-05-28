package com.bellng.scorecounter.di

import com.bellng.scorecounter.ui.dashboard.DashboardViewModel
import com.bellng.scorecounter.viewmodel.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Bell on 28-May-17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun getDashboardViewModel(): DashboardViewModel

}