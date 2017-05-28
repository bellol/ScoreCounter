package com.bellng.scorecounter.di

import com.bellng.scorecounter.viewmodel.ViewModelFactory
import com.bellng.scorecounter.dashboard.DashboardViewModel
import com.bellng.scorecounter.model.CounterModel
import dagger.Module
import dagger.Provides

/**
 * Created by Bell on 28-May-17.
 */
@Module
class AppModule {

    @Provides fun provideViewModelFactory(appComponent: AppComponent): ViewModelFactory = ViewModelFactory(appComponent)

    @Provides fun provideDashboardViewModel(counterModel: CounterModel) = DashboardViewModel(counterModel)

    @Provides fun provideCounterModel() = CounterModel()

}