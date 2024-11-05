package com.wcd.farm.di

import androidx.lifecycle.ViewModelProvider
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.MavericksViewModelComponent
import com.airbnb.mvrx.hilt.ViewModelKey
import com.wcd.farm.presentation.state.MemorialViewState
import com.wcd.farm.presentation.viewmodel.AppViewModel
import com.wcd.farm.presentation.viewmodel.DiseaseViewModel
import com.wcd.farm.presentation.viewmodel.HomeViewModel
import com.wcd.farm.presentation.viewmodel.MemorialViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
interface AppViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AppViewModel::class)
    fun appViewModelFactory(factory: AppViewModel.Factory): AssistedViewModelFactory<*, *>


    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun homeViewModelFactory(factory: HomeViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(DiseaseViewModel::class)
    fun diseaseViewModelFactory(factory: DiseaseViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(MemorialViewModel::class)
    fun memorialViewModelFactory(factory: MemorialViewModel.Factory): AssistedViewModelFactory<*, *>


}