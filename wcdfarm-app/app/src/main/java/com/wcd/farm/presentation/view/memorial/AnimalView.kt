package com.wcd.farm.presentation.view.memorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.data.model.HarmDTO
import com.wcd.farm.presentation.viewmodel.MemorialViewModel

@Composable
fun AnimalScreen() {
    val viewModel: MemorialViewModel = mavericksViewModel()
    val listState = rememberLazyListState()
    val gardenList by viewModel.gardenList.collectAsState()
    val crtGarden by viewModel.crtGarden.collectAsState()

    val animalList by viewModel.harmAnimalList.collectAsState()

    if (animalList.isEmpty()) {
        EmptyListView(notify = "방문 이력이 없습니다.")
    }

    LaunchedEffect(crtGarden) {
        crtGarden?.let { viewModel.getHarmAnimalList(gardenList[it].gardenId) }
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.Top),
        modifier = Modifier
            .fillMaxHeight(),
        state = listState
    ) {
        items(animalList) { animal ->
            val harm = HarmDTO(harmPictureId = animal.harmPictureId, null, harmPicture = animal.image, createdDate = animal.createdDate, harmTarget = animal.animalType)
            InvasionView(harm, "방문")
        }
    }
}