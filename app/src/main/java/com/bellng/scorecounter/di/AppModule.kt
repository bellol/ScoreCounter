package com.bellng.scorecounter.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.bellng.scorecounter.CounterApp
import com.bellng.scorecounter.model.CounterStorage
import com.bellng.scorecounter.ui.dashboard.DashboardViewModel
import com.bellng.scorecounter.model.CounterModel
import com.bellng.scorecounter.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by Bell on 28-May-17.
 */
@Module
class AppModule(val counterApp: CounterApp) {

    @Provides fun provideCounterApp(): CounterApp = counterApp

    @Provides fun provideViewModelFactory(appComponent: AppComponent): ViewModelFactory = ViewModelFactory(appComponent)

    @Provides fun provideDashboardViewModel(counterModel: CounterModel): DashboardViewModel = DashboardViewModel(counterModel)

    @Provides fun provideCounterModel(counterStorage: CounterStorage): CounterModel = CounterModel(counterStorage)

    @Provides fun provideCounterStorage(sharedPreferences: SharedPreferences): CounterStorage = CounterStorage(sharedPreferences)

    @Provides fun provideSharedPreferences(counterApp: CounterApp): SharedPreferences = counterApp.getSharedPreferences("shared_preferences", MODE_PRIVATE)

}