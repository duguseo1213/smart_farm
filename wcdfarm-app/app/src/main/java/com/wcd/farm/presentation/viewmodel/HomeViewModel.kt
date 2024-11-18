package com.wcd.farm.presentation.viewmodel

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.wcd.farm.data.repository.GardenRepository
import com.wcd.farm.data.repository.MemorialRepository
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
    @Assisted initialState: HomeViewState,
    private val gardenRepository: GardenRepository,
    private val memorialRepository: MemorialRepository
): MavericksViewModel<HomeViewState>(initialState) {

    private val homeViewIntent = Channel<HomeViewIntent>()

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<HomeViewModel, HomeViewState> {
        override fun create(state: HomeViewState): HomeViewModel
    }

    companion object : MavericksViewModelFactory<HomeViewModel, HomeViewState> by hiltMavericksViewModelFactory()

    val crtGarden = gardenRepository.crtGarden
    val gardenStreamKeyMap = gardenRepository.gardenStreamKeyMap
    val newPicture = memorialRepository.newPicture
    val crtWeatherGardenIndex = gardenRepository.crtWeatherGardenIndex

    init {
        handleIntent()
    }

    val gardenList = gardenRepository.gardenList

    fun sendIntent(intent: HomeViewIntent) = viewModelScope.launch(Dispatchers.Main) {
        homeViewIntent.send(intent)
    }

    private fun handleIntent() {
        viewModelScope.launch(Dispatchers.Main) {
            homeViewIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    HomeViewIntent.ArriveFarm -> setState { copy(isUserOnFarm = true) }
                    HomeViewIntent.LeaveFarm -> setState { copy(isUserOnFarm = false) }
                    HomeViewIntent.ShowWeekWeather -> setState { copy(showWeekWeather = true) }
                    HomeViewIntent.HideWeekWeather -> setState { copy(showWeekWeather = false) }
                    HomeViewIntent.ShowFarmImage -> setState { copy(displayType = HomeViewState.IMAGE) }
                    HomeViewIntent.ShowFarmLive -> setState { copy(displayType = HomeViewState.LIVE) }
                }
            }
        }
    }

    fun getGardenList() {
        gardenRepository.getGardenList()
    }

    fun requestWatering() {
        gardenRepository.requestRemoteWater()
    }

    fun requestFilm() {
        gardenRepository.requestTakePicture()
    }

    fun getStreamKeys() {
        gardenRepository.getStreamKeys()
    }

    fun addGarden(gardenId: Long) {
        gardenRepository.addGarden(gardenId)
    }

    fun showNewPicture(imageUrl: String?) {
        memorialRepository.setNewPicture(imageUrl)
    }

    fun addNewPicture(imageUrl: String, description: String, gardenId: Long) {
        memorialRepository.addNewPicture(imageUrl, description, gardenId)
    }

    fun changeGardenName(gardenId: Long, gardenName: String) {
        gardenRepository.changeGardenName(gardenId, gardenName)
    }

    fun setCrtGarden(index: Int) {
        gardenRepository.setCrtGarden(index)
    }

    fun setCrtWeatherGardenIndex(index: Int) {
        gardenRepository.setCrtWeatherGardenIndex(index)
    }

    fun toggleHarm(gardenId: Long) {
        gardenRepository.toggleHarm(gardenId)
    }
}