package com.wcd.farm.presentation.viewmodel

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.wcd.farm.presentation.intent.HomeViewIntent
import com.wcd.farm.presentation.state.HomeViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class HomeViewModel @AssistedInject constructor(
    @Assisted initialState: HomeViewState
): MavericksViewModel<HomeViewState>(initialState) {

    private val homeViewIntent = Channel<HomeViewIntent>()

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<HomeViewModel, HomeViewState> {
        override fun create(state: HomeViewState): HomeViewModel
    }

    companion object : MavericksViewModelFactory<HomeViewModel, HomeViewState> by hiltMavericksViewModelFactory()

    init {
        handleIntent()
    }

    fun sendIntent(intent: HomeViewIntent) = viewModelScope.launch(Dispatchers.Main) {
        homeViewIntent.send(intent)
    }

    private fun handleIntent() {
        viewModelScope.launch(Dispatchers.Main) {
            homeViewIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    HomeViewIntent.ArriveFarm -> setState { copy(isUserOnFarm = true) }
                    HomeViewIntent.LeaveFarm -> setState { copy(isUserOnFarm = false) }
                }
            }
        }
    }
}