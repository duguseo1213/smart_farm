package com.wcd.farm.presentation.viewmodel

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.wcd.farm.data.repository.GardenRepository
import com.wcd.farm.data.repository.MemorialRepository
import com.wcd.farm.presentation.intent.MemorialViewIntent
import com.wcd.farm.presentation.state.MemorialViewState
import com.wcd.farm.presentation.view.memorial.ANIMAL_VIEW
import com.wcd.farm.presentation.view.memorial.GALLERY_VIEW
import com.wcd.farm.presentation.view.memorial.GROWTH_VIEW
import com.wcd.farm.presentation.view.memorial.THEFT_VIEW
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class MemorialViewModel @AssistedInject constructor(
    @Assisted initialState: MemorialViewState,
    private val repository: MemorialRepository,
    private val gardenRepository: GardenRepository
) :
    MavericksViewModel<MemorialViewState>(initialState) {

    private val memorialViewIntent = Channel<MemorialViewIntent>()

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MemorialViewModel, MemorialViewState> {
        override fun create(state: MemorialViewState): MemorialViewModel
    }

    companion object : MavericksViewModelFactory<MemorialViewModel, MemorialViewState> by hiltMavericksViewModelFactory()

    val selectedDate = repository.selectedDate
    val pictureList = repository.pictureList
    val timeLapseList = repository.timeLapseImageList
    val crtTimeLapseImage = repository.crtTimeLapseImage
    val harmAnimalList = repository.harmAnimalList
    val harmTheftList = repository.harmTheftList

    val crtGarden = gardenRepository.crtGarden

    init {
        handleIntent()
    }

    fun sendIntent(intent: MemorialViewIntent) = viewModelScope.launch(Dispatchers.Main) {
        memorialViewIntent.send(intent)
    }

    private fun handleIntent() {
        viewModelScope.launch(Dispatchers.Main) {
            memorialViewIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    MemorialViewIntent.HideDialog -> setState { copy(showDialog = false) }
                    MemorialViewIntent.ShowDialog -> setState { copy(showDialog = true) }

                    MemorialViewIntent.ShowGalleryView -> setState { copy(showMemorialView = GALLERY_VIEW) }
                    MemorialViewIntent.ShowGrowthView -> setState { copy(showMemorialView = GROWTH_VIEW) }
                    MemorialViewIntent.ShowAnimalView -> setState { copy(showMemorialView = ANIMAL_VIEW) }
                    MemorialViewIntent.ShowTheftView -> setState { copy(showMemorialView = THEFT_VIEW) }
                }
            }
        }
    }

    fun setSelectedDate(date: LocalDate) {
        repository.setSelectedDate(date)
    }

    fun getAllPictures(gardenId: Long) {
        repository.getAllPictures(gardenId)
    }

    fun getHarmAnimalList(gardenId: Long) {
        repository.getHarmAnimalList(gardenId)
    }

    fun getHarmTheftList(gardenId: Long) {
        repository.getHarmTheftList(gardenId)
    }

    fun getTimeLapse(gardenId: Long) {
        repository.getTimeLapse(gardenId)
    }
}