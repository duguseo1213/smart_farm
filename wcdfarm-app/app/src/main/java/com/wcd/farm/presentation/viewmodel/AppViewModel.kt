package com.wcd.farm.presentation.viewmodel

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.wcd.farm.data.repository.GardenRepository
import com.wcd.farm.data.repository.ServerRepository
import com.wcd.farm.presentation.intent.AppViewIntent
import com.wcd.farm.presentation.state.AppViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AppViewModel @AssistedInject constructor(@Assisted initialState: AppViewState, private val repository: ServerRepository) :
    MavericksViewModel<AppViewState>(initialState) {
    private val appViewIntent = Channel<AppViewIntent>()

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<AppViewModel, AppViewState> {
        override fun create(state: AppViewState): AppViewModel
    }

    companion object :
        MavericksViewModelFactory<AppViewModel, AppViewState> by hiltMavericksViewModelFactory()

    init {
        handleIntent()
    }

    val loginState = repository.loginState

    fun sendIntent(intent: AppViewIntent) = viewModelScope.launch(Dispatchers.Main) {
        appViewIntent.send(intent)
    }

    private fun handleIntent() {
        viewModelScope.launch(Dispatchers.Main) {
            appViewIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    AppViewIntent.ShowLoginView -> setState { copy(appViewState = AppViewState.LOGIN) }
                    AppViewIntent.ShowMainView -> setState { copy(appViewState = AppViewState.MAIN) }
                }
            }
        }
    }

    fun requestRefreshToken() {
        repository.requestRefreshToken()
    }

    fun setAccessToken(accessToken: String) {
        repository.setAccessToken(accessToken)
    }

    fun setRefreshToken(refreshToken: String) {
        repository.setRefreshToken(refreshToken)
    }

    fun setLoginState(state: Boolean) {
        repository.setLoginState(state)
    }

    fun getLoginState() {
        repository.getLoginState()
    }
}
