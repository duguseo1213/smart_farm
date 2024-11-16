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
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.data.model.HarmDTO
import com.wcd.farm.presentation.viewmodel.MemorialViewModel

@Composable
fun TheftScreen() {
    val viewModel: MemorialViewModel = mavericksViewModel()
    val listState = rememberLazyListState()
    val crtGardenId by viewModel.crtGarden.collectAsState()
    val theftList by viewModel.harmTheftList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getHarmTheftList(crtGardenId)
    }

    if (theftList.isEmpty()) {
        EmptyListView(notify = "도난 이력이 없습니다.")
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.Top),
        modifier = Modifier
            .fillMaxHeight(),
        state = listState
    ) {
        items(theftList) { theft ->
            val harm = HarmDTO(harmPictureId = theft.harmPictureId, null, harmPicture = theft.image, createdDate = theft.createdDate, harmTarget = null)
            InvasionView(harm, "침입")
        }
    }
}